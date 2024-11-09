package me.kubister11.bytepanel.shared.image.test

import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.repository.MongoRepository

class TestImageRepository : MongoRepository<String, DockerImage> {

    val images = mutableListOf(
        DockerImage(
            "test-minecraft",
            "FROM openjdk:17-jdk-slim\n" +
                    "RUN apt-get update && apt-get install -y curl\n" +
                    "RUN curl -o server.jar https://api.papermc.io/v2/projects/paper/versions/1.20.4/builds/497/downloads/paper-1.20.4-497.jar\n" +
                    "RUN echo \"eula=true\" > eula.txt\n"
        ),
    )

    override fun findAll(): Collection<DockerImage> {
        return images
    }

    override fun delete(id: String) {
        images.removeAll { it.name == id }
    }

    override fun update(id: String, value: DockerImage) {
        images.removeAll { it.name == id }
        images.add(value)
    }

    override fun insert(value: DockerImage) {
        images.add(value)
    }

    override fun findById(id: String): DockerImage? {
        return images.firstOrNull { it.name == id }
    }
}