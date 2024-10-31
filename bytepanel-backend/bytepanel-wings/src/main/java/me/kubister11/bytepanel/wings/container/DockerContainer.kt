package me.kubister11.bytepanel.wings.container

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.AttachContainerCmd
import com.github.dockerjava.api.command.PullImageResultCallback
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.github.dockerjava.core.command.AttachContainerResultCallback
import me.kubister11.bytepanel.wings.logger.DockerContainerLogger
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream

class DockerContainer(
    private val dockerClient: DockerClient,
    image: String,
    name: String,
    createCommand: String,
    exposedPorts: List<Int>,
) {
    private val containerId: String
    val logger = DockerContainerLogger(dockerClient, name)

    init {
        dockerClient.pullImageCmd(image)
            .exec(PullImageResultCallback())
            .awaitCompletion()

        val containerResponse = dockerClient.createContainerCmd(image)
            .withStdinOpen(true)
            .withTty(false)
            .withName(name)
            .withCmd("sh", "-c", createCommand)
            .withExposedPorts(exposedPorts.map { ExposedPort.tcp(it) })
            .withPortBindings(exposedPorts.map { PortBinding(Ports.Binding.bindPort(it), ExposedPort.tcp(it)) })
            .exec()

        this.containerId = containerResponse.id
        println("Created container with id: $containerId")
    }

    fun start() {
        dockerClient.startContainerCmd(containerId).exec()
        logger.start()
    }


    private fun executeCommand(command: String) {
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
}