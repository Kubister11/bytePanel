package me.kubister11.bytepanel.shared.database

import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.BsonDocument
import org.bson.BsonInt64
import org.bson.conversions.Bson

class MongoDB(
    private val settings: MongoClientSettings,
    private val databaseName: String
) {
    private lateinit var mongoClient: MongoClient
    lateinit var mongoDatabase: MongoDatabase

    fun connect(): Boolean {
        mongoClient = MongoClients.create(settings)
        mongoDatabase = mongoClient.getDatabase(databaseName)
        return try {
            val command: Bson = BsonDocument("ping", BsonInt64(1))
            mongoDatabase.runCommand(command)
            true
        } catch (exception: MongoException) {
            false
        }
    }

    fun close() {
        mongoClient.close()
    }


}
