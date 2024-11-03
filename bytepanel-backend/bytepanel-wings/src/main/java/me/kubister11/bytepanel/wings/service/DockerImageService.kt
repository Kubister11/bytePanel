package me.kubister11.bytepanel.wings.service

import com.github.dockerjava.api.DockerClient
import me.kubister11.bytepanel.shared.image.DockerImage
import java.io.File
import java.nio.file.Files

class DockerImageService(
    private val dockerClient: DockerClient,
) {

    fun buildImage(image: DockerImage) {
        val dockerfileDir = Files.createTempDirectory("docker-image-builder").toFile()
        val dockerfile = File(dockerfileDir, "Dockerfile")

        try {
            dockerfile.bufferedWriter().use { writer ->
                writer.write(image.instructions)
            }

            val id = this.dockerClient.buildImageCmd(dockerfile)
                .withTags(setOf(image.name))
                .start()
                .awaitImageId()

            println("Built Docker image with tag: ${image.name} ($id)")
        } finally {
            dockerfile.delete()
            dockerfileDir.delete()
        }
    }

    fun imageExists(tag: String): Boolean {
        return dockerClient.listImagesCmd()
            .exec()
            .any {
                it.repoTags?.any { tag.split(":")[0] == tag } == true
            }
    }
}