package me.kubister11.bytepanel.wings.container

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.AttachContainerCmd
import com.github.dockerjava.api.command.ExecCreateCmdResponse
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
    val image: String,
    val name: String,
    val createCommand: String,
    val startCommand: String,
    val stopCommand: String,
    val exposedPorts: List<Int>,

    var containerId: String? = null
) {

    val logger = DockerContainerLogger(dockerClient, name)

    fun create() {
        dockerClient.pullImageCmd(image)
            .exec(PullImageResultCallback())
            .awaitCompletion()

        val containerResponse = dockerClient.createContainerCmd(image)
            .withStdinOpen(true)
            .withTty(false)
            .withCmd(createCommand)
            .withEntrypoint("sh", "-c")
            .withName(name)
            .withExposedPorts(exposedPorts.map { ExposedPort.tcp(it) })
            .withPortBindings(exposedPorts.map { PortBinding(Ports.Binding.bindPort(it), ExposedPort.tcp(it)) })
            .exec()

        this.containerId = containerResponse.id
        println("Created container with id: $containerId")
    }

    fun install() {
        this.runContainer()

        val execCreateCmdResponse: ExecCreateCmdResponse = dockerClient.execCreateCmd(containerId!!)
            .withCmd("sh", "-c", startCommand)
            .withAttachStdout(true)
            .withAttachStderr(true)
            .exec()

        dockerClient.execStartCmd(execCreateCmdResponse.id).exec(AttachContainerResultCallback())
        println("Container $containerId has been installed.")


//        this.stop()
    }

    private fun runContainer() {
        dockerClient.startContainerCmd(containerId!!).exec()
        logger.start()
    }

    fun start() {
        dockerClient.startContainerCmd(containerId!!).exec()
    }

    fun stop() {
        this.executeCommand(stopCommand)
    }

    fun kill() {
        dockerClient.stopContainerCmd(containerId!!).exec()
        logger.close()
        println("Container $containerId has been stopped.")
    }


    private fun executeCommand(command: String) {
        try {
            val out = PipedOutputStream()
            val `in` = PipedInputStream(out)
            val writer = BufferedWriter(OutputStreamWriter(out))

            val attachContainerCmd: AttachContainerCmd = dockerClient.attachContainerCmd(containerId!!)
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