package me.kubister11.bytepanel.shared.server

import com.google.gson.Gson
import com.mongodb.client.model.Filters
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.repository.MongoRepository
import org.bson.Document
import org.bson.json.JsonWriterSettings

class ServerRepository(
    mongoDB: MongoDB,
    private val gson: Gson
) : MongoRepository<String, Server> {

    private val jsonWriterSettings = JsonWriterSettings.builder().build()
    private val collection = mongoDB.mongoDatabase.getCollection("servers")

    override fun findAll(): Collection<Server> {
        return collection.find()
            .map { deserialize(it) }
            .toList()
    }

    override fun delete(id: String) {
        collection.deleteOne(Filters.eq("_id", id))
    }

    override fun update(id: String, value: Server) {
        collection.replaceOne(Filters.eq("_id", id), serialize(value))
    }

    override fun insert(value: Server) {
        collection.insertOne(serialize(value))
    }

    override fun findById(id: String): Server? {
        val document = collection.find(Filters.eq("_id", id)).firstOrNull() ?: return null
        return deserialize(document)
    }

    private fun serialize(server: Server): Document {
        return Document.parse(gson.toJson(server))
    }

    private fun deserialize(document: Document): Server {
        return gson.fromJson(document.toJson(jsonWriterSettings), Server::class.java)
    }
}