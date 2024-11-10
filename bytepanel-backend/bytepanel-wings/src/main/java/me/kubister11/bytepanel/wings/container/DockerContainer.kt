package me.kubister11.bytepanel.wings.container

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.command.AttachContainerCmd
import com.github.dockerjava.api.command.ExecCreateCmdResponse
import com.github.dockerjava.api.command.PullImageResultCallback
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import com.github.dockerjava.core.command.AttachContainerResultCallback
import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.wings.logger.DockerContainerLogger
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.io.PipedInputStream
import java.io.PipedOutputStream

class DockerContainer(
    private val dockerClient: DockerClient,
    val image: DockerImage,
    private val name: String,
    private val startCommand: String,
    private val stopCommand: String,
    private val exposedPorts: List<Int>,

    private val memoryLimit: Long, //MB
    private val cpuLimit: Long, //Percent
    private val diskLimit: Long, //MB

    var containerId: String? = null
) {

    val logger = DockerContainerLogger(dockerClient, name)
    private var dockerThread: Thread? = null

    fun create() {
//        dockerClient.pullImageCmd(image)
//            .exec(PullImageResultCallback())
//            .awaitCompletion()

        val containerCmd = dockerClient.createContainerCmd(image.name)
            .withStdinOpen(true)
            .withTty(false)
            .withCmd("sh", "-c", startCommand)
            .withName(name)
            .withExposedPorts(exposedPorts.map { ExposedPort.tcp(it) })
            .withPortBindings(exposedPorts.map { PortBinding(Ports.Binding.bindPort(it), ExposedPort.tcp(it)) })

        val hostConfig = containerCmd.hostConfig ?: HostConfig.newHostConfig()
        containerCmd.withHostConfig(
            hostConfig
                .withMemory(memoryLimit * 1024 * 1024)
                .withCpuQuota(cpuLimit * 1000)
                .withDiskQuota(diskLimit * 1024 * 1024)
        )

        val containerResponse = containerCmd.exec()

        this.containerId = containerResponse.id
        println("Created container with id: $containerId")
    }


    fun start() {
        this.dockerThread = Thread {
            dockerClient.startContainerCmd(containerId!!).exec()
            logger.start()
        }.also { it.start() }
    }

    fun stop() {
        this.executeCommand(stopCommand)
        this.logger.close()
    }

    fun kill() {
        dockerClient.stopContainerCmd(containerId!!).exec()
        logger.close()
        println("Container $containerId has been killed.")
    }


    fun executeCommand(command: String) {
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