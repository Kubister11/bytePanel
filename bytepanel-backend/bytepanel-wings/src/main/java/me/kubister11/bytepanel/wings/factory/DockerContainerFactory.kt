package me.kubister11.bytepanel.wings.factory

import com.github.dockerjava.api.DockerClient
import me.kubister11.bytepanel.shared.Wings
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.packets.ConsoleLogPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.wings.container.DockerContainer
import me.kubister11.bytepanel.wings.repository.DockerContainerLocalRepository
import org.apache.commons.lang3.RandomStringUtils
import java.util.concurrent.CompletableFuture

class DockerContainerFactory(
    private val dockerClient: DockerClient,
    private val serverRepository: MongoRepository<String, Server>,
    private val dockerContainerLocalRepository: DockerContainerLocalRepository,
    private val redis: RedisAPI
) {

    fun createContainer(
        server: Server,
        dockerImage: DockerImage,
        init: Boolean = true
    ): CompletableFuture<DockerContainer> {
        return CompletableFuture.supplyAsync {
            val container = DockerContainer(
                dockerClient,
                dockerImage,
                server.name,
                server.startCommand,
                server.stopCommand,
                server.exposedPorts,
                server.memoryUsageMax,
                server.cpuLoadMax,
                server.diskUsageMax
            )

            container.logger.addListener { line ->
                redis.publishAsync(
                    Wings.CONSOLE_TOPIC,
                    ConsoleLogPacket(
                        server.id,
                        line
                    )
                )
            }

            if (init) {
                dockerContainerLocalRepository.initContainer(container)
                this.serverRepository.insert(server)
            } else {
                container.containerId = server.containerId
                dockerContainerLocalRepository.addContainer(container)
            }

            return@supplyAsync container
        }
    }

}