package me.kubister11.bytepanel.wings.logger

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import java.util.function.Consumer

class DockerContainerLogger(
    private val dockerClient: DockerClient,
    private val containerId: String,
) {

    private val listeners: MutableList<Consumer<String>> = mutableListOf()
    private var callback: ResultCallback.Adapter<Frame>? = null

    fun addListener(listener: Consumer<String>) {
        listeners.add(listener)
    }

    fun start() {
        if (callback != null) error("Logger is already started")

        this.callback = dockerClient.logContainerCmd(containerId)
            .withStdOut(true)
            .withStdErr(true)
            .withFollowStream(true)
            .exec(object : ResultCallback.Adapter<Frame>() {
                override fun onNext(item: Frame) {
                    val logLine = String(item.payload)
                    listeners.forEach { listener -> listener.accept(logLine) }
                    println(logLine)
                }
            }).awaitCompletion()
    }

    fun close() {
        if (callback == null) error("Logger is not started")

        callback?.close()
    }
}