package me.kubister11.bytepanel.wings.service

import com.github.dockerjava.api.DockerClient
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.wings.container.DockerContainer
import me.kubister11.bytepanel.wings.container.DockerServer
import org.apache.commons.lang3.RandomStringUtils
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ServerService(
    private val dockerClient: DockerClient,
    private val serverRepository: MongoRepository<String, Server>,
    private val wingsId: String,
) {

    companion object {
        val serverCreatinonExecutor = Executors.newSingleThreadExecutor()
    }

    private val containers: MutableMap<String, DockerContainer> = mutableMapOf()

    fun createServer(
        image: String,
        name: String,
        createCommand: String,
        startCommand: String,
        stopCommand: String,
        exposedPorts: List<Int>,

        maxCpuLoad: Int,
        maxMemoryUsage: Long,
        maxDiskUsage: Long,
    ): CompletableFuture<DockerServer> {
        return CompletableFuture.supplyAsync {
            val container = DockerContainer(dockerClient, image, name, createCommand, startCommand, stopCommand, exposedPorts)
            container.create()
            container.install()
            containers[container.containerId!!] = container

            return@supplyAsync DockerServer(
                    Server(
                        RandomStringUtils.random(50, true, true),
                        name,
                        maxCpuLoad,
                        maxMemoryUsage,
                        maxDiskUsage,
                        this.wingsId,
                        container.containerId!!
                    ), container
            )
        }
    }

}