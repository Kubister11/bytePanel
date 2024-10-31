package me.kubister11.bytepanel.wings.service

import com.github.dockerjava.api.DockerClient
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors

class ServerService(
    private val dockerClient: DockerClient,
    private val serverRepository: MongoRepository<String, Server>
) {

    companion object {
        val serverCreatinonExecutor = Executors.newSingleThreadExecutor()
    }

    fun createServer(
        image: String,
        name: String,
        createCommand: String,
        exposedPorts: List<Int>
    ): CompletableFuture<Server> {
        val future = CompletableFuture<Server>()

        serverCreatinonExecutor.submit {

        }

        return future
    }

}