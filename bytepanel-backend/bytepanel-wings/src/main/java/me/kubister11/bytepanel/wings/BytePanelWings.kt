package me.kubister11.bytepanel.wings

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.model.*
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient

class BytePanelWings {
    fun start() {
        // Tworzymy instancję klienta Docker

        val config =
            DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://localhost:2375").build()
        val dockerHost = ApacheDockerHttpClient.Builder().dockerHost(config.dockerHost).build()

        val dockerClient: DockerClient = DockerClientImpl.getInstance(
            config, dockerHost
        )

        // Pobieramy obraz serwera Minecraft z Docker Hub (itzg/minecraft-server)
        dockerClient.pullImageCmd("itzg/minecraft-server").start().awaitCompletion()

        // Definiujemy port, na którym będzie uruchomiony serwer Minecraft (domyślnie 25565)
        val exposedPort = ExposedPort.tcp(25565)
        val portBinding = PortBinding.parse("25565:25565")

        // Definiujemy wolumen, gdzie będą przechowywane dane serwera Minecraft na hoście (opcjonalne)
        val volume = Volume("/data")

        // Tworzymy kontener z odpowiednią konfiguracją
        val container: CreateContainerResponse = dockerClient.createContainerCmd("itzg/minecraft-server")
            .withName("minecraft-server")
            .withExposedPorts(exposedPort)
            .withHostConfig(
                HostConfig().withPortBindings(portBinding).withBinds(Bind("/local/path/to/minecraft", volume))
            )
            .exec()

        // Uruchamiamy kontener
        dockerClient.startContainerCmd(container.id).exec()

        println("Serwer Minecraft został uruchomiony w kontenerze o ID: ${container.id}")
    }
}