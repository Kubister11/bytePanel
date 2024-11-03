package me.kubister11.bytepanel.wings.repository

import me.kubister11.bytepanel.wings.container.DockerContainer
import me.kubister11.bytepanel.wings.service.DockerImageService

class DockerContainerLocalRepository(
    private val imageService: DockerImageService
) {

    private val containers: MutableMap<String, DockerContainer> = mutableMapOf()

    fun initContainer(container: DockerContainer) {
        this.imageService.buildImage(container.image)
        container.create()

        containers[container.containerId!!] = container
    }

    fun addContainer(container: DockerContainer) {
        containers[container.containerId!!] = container
    }

    fun findById(id: String): DockerContainer? {
        println("size: ${containers.size}")
        println(containers.keys.joinToString(", "))

        return containers[id]
    }

}