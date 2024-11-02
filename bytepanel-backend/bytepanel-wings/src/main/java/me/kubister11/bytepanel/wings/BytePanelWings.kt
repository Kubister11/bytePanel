package me.kubister11.bytepanel.wings

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import me.kubister11.bytepanel.shared.server.test.TestServerRepository
import me.kubister11.bytepanel.wings.container.DockerContainer
import me.kubister11.bytepanel.wings.service.ServerService
import org.apache.commons.lang3.RandomStringUtils


class BytePanelWings {
    val dockerClient = createDockerClient()

    lateinit var wingsId: String
    lateinit var serverService: ServerService

    fun start() {
        this.wingsId = RandomStringUtils.random(50, true, true)

        this.serverService = ServerService(dockerClient, TestServerRepository(), wingsId)

        val dockerServer = this.serverService.createServer(
            "openjdk:17-jdk-slim",
            "minecraft-server",
            """
            apt-get update && apt-get install -y curl && \
            curl -o server.jar https://api.papermc.io/v2/projects/paper/versions/1.20.4/builds/497/downloads/paper-1.20.4-497.jar && \
            echo "eula=true" > eula.txt
        """.trimIndent(),
            "java -jar server.jar nogui",
            "stop",
            listOf(25565),
            100,
            1024 * 3,
            1024 * 30
        ).join()

        println("TEST")
        println("TEST")
        println("TEST")
        println("TEST")
        println("TEST")
        println("TEST")
        println("TEST")

        dockerServer.container.start()


    }

    private fun createDockerClient(): DockerClient {
        val config =
            DefaultDockerClientConfig.createDefaultConfigBuilder().build()
        val dockerHost = ApacheDockerHttpClient.Builder().dockerHost(config.dockerHost).build()

        return DockerClientImpl.getInstance(
            config, dockerHost
        )
    }


    fun createDockerContainer(dockerClient: DockerClient): CreateContainerResponse {
        return dockerClient.createContainerCmd("openjdk:17-jdk-slim")
            .withStdinOpen(true)
            .withTty(false)
            .withName("minecraft-server")
            .withCmd("sh", "-c", """
            apt-get update && apt-get install -y curl && \
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

}