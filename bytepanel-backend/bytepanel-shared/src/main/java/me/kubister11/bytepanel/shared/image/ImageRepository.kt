package me.kubister11.bytepanel.shared.image

import com.google.gson.Gson
import com.mongodb.client.model.Filters
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.repository.MongoRepository
import org.bson.Document
import org.bson.json.JsonWriterSettings

class ImageRepository(
    mongoDB: MongoDB,
    private val gson: Gson
) : MongoRepository<String, DockerImage> {

    private val jsonWriterSettings = JsonWriterSettings.builder().build()
    private val collection = mongoDB.mongoDatabase.getCollection("images")

    override fun findAll(): Collection<DockerImage> {
        return collection.find()
            .map { deserialize(it) }
            .toList()
    }

    override fun delete(id: String) {
        collection.deleteOne(Filters.eq("_id", id))
    }

    override fun update(id: String, value: DockerImage) {
        collection.replaceOne(Filters.eq("_id", id), serialize(value))
    }

    override fun insert(value: DockerImage) {
        collection.insertOne(serialize(value))
    }

    override fun findById(id: String): DockerImage? {
        val document = collection.find(Filters.eq("_id", id)).firstOrNull() ?: return null
        return deserialize(document)
    }

    private fun serialize(server: DockerImage): Document {
        return Document.parse(gson.toJson(server))
    }

    private fun deserialize(document: Document): DockerImage {
        return gson.fromJson(document.toJson(jsonWriterSettings), DockerImage::class.java)
    }
}