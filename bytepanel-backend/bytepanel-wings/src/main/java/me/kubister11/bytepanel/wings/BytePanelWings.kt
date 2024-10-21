package me.kubister11.bytepanel.wings

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.command.AttachContainerCmd
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.PullImageResultCallback
import com.github.dockerjava.api.model.*
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.core.command.AttachContainerResultCallback
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.io.*
import java.net.InetSocketAddress


class BytePanelWings {
    fun start() {
        // Krok 1: Tworzymy klienta Dockera
        val dockerClient = createDockerClient()

        // Krok 2: Pobieramy obraz Javy dla kontenera Minecrafta
        pullMinecraftServerImage(dockerClient)

        // Krok 3: Tworzymy kontener
        val container = createDockerContainer(dockerClient)

        println("Copying Minecraft server JAR to container...")
        println("Minecraft server JAR copied to container!")
        // Krok 5: Uruchamiamy kontener
        startContainer(dockerClient, container.id)

        // Krok 6: Strumieniujemy logi konsoli po uruchomieniu serwera
        println("Serwer Minecraft został uruchomiony w kontenerze Docker!")
        println("Logi konsoli:")
//
        val logWebSocketServer = LogWebSocketServer(
            2137,
            dockerClient, container.id
        )
        logWebSocketServer.start()

        println("Naciśnij Enter, aby zamknąć serwer...")
        readlnOrNull()
        logWebSocketServer.stop()
    }

    fun createDockerClient(): DockerClient {
        val config =
            DefaultDockerClientConfig.createDefaultConfigBuilder().build()
        val dockerHost = ApacheDockerHttpClient.Builder().dockerHost(config.dockerHost).build()

        return DockerClientImpl.getInstance(
            config, dockerHost
        )
    }

    fun pullMinecraftServerImage(dockerClient: DockerClient) {
        // Pobieramy oficjalny obraz Javy (możemy dostosować wersję)
        dockerClient.pullImageCmd("openjdk:17-jdk-slim")
            .exec(PullImageResultCallback())
            .awaitCompletion()
    }

    fun createDockerContainer(dockerClient: DockerClient): CreateContainerResponse {
        // Tworzymy nowy kontener z serwerem Minecraft
        return dockerClient.createContainerCmd("openjdk:17-jdk-slim")
            .withStdinOpen(true)
            .withTty(false)
            .withName("minecraft-server")
            .withCmd("sh", "-c", """
            apt-get update && apt-get install -y curl && apt-get install -y screen && \
            curl -o server.jar https://api.papermc.io/v2/projects/paper/versions/1.20.4/builds/497/downloads/paper-1.20.4-497.jar && \
            echo "eula=true" > eula.txt && \
            touch server-console-input && \
            java -Xmx1024M -Xms1024M -jar server.jar nogui
        """.trimIndent())
            .withEnv("EULA=TRUE")
            .withExposedPorts(ExposedPort.tcp(25565))
            .withPortBindings(PortBinding(Ports.Binding.bindPort(25565), ExposedPort.tcp(25565)))
            .exec()
    }

    fun startContainer(dockerClient: DockerClient, containerId: String) {
        // Uruchamiamy kontener
        dockerClient.startContainerCmd(containerId).exec()
    }


    class LogWebSocketServer(port: Int, private val dockerClient: DockerClient, private val containerId: String) : WebSocketServer(
        InetSocketAddress(port)
    ) {

        private val logs = mutableListOf<String>()

        // Lista wszystkich połączonych klientów WebSocket
        private val connectedClients = mutableListOf<WebSocket>()

        override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
            conn?.let {
                connectedClients.add(it)
                logs.forEach { log ->
                    it.send(log)
                }
                println("Nowe połączenie: ${it.remoteSocketAddress}")
            }
        }

        override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
            connectedClients.remove(conn)
            println("Połączenie zamknięte: ${conn?.remoteSocketAddress}")
        }

        override fun onMessage(conn: WebSocket?, message: String?) {
            // Obsługa komunikatów od klienta (komendy z frontendu)
            message?.let {
                println("Otrzymano komendę: $message od ${conn?.remoteSocketAddress}")
                executeDockerCommand(it)
            }
        }

        override fun onError(conn: WebSocket?, ex: Exception?) {
            println("Błąd: ${ex?.message}")
            ex?.printStackTrace()
        }

        override fun onStart() {
            println("Serwer WebSocket uruchomiony na porcie ${this.port}")
            streamDockerLogs()
        }

        private fun executeDockerCommand(command: String) {
            try {
                val out = PipedOutputStream()
                val `in` = PipedInputStream(out)
                val writer = BufferedWriter(OutputStreamWriter(out))

                val attachContainerCmd: AttachContainerCmd = dockerClient.attachContainerCmd(containerId)
                    .withStdIn(`in`)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)

                attachContainerCmd.exec(AttachContainerResultCallback())

                writer.write("$command\n")
                writer.flush()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        // Funkcja do przesyłania logów z kontenera Docker do klientów WebSocket
        private fun streamDockerLogs() {
            dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .withFollowStream(true)
                .exec(object : ResultCallback.Adapter<Frame>() {
                    override fun onNext(item: Frame) {
                        val logLine = String(item.payload)  // Konwertujemy logi do stringa
                        // Wysyłamy logi do wszystkich połączonych klientów WebSocket
                        println(logLine)
                        logs.add(logLine)
                        connectedClients.forEach { client ->
                            client.send(logLine)
                        }
                    }
                })
        }
    }
}