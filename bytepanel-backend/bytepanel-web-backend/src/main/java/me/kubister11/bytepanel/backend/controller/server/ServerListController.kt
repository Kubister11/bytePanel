package me.kubister11.bytepanel.backend.controller.server

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.kubister11.bytepanel.shared.Shared
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.packets.CreateServerPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity
import spark.Request
import spark.Response
import spark.Route

class ServerListController(
    private val gson: Gson,
    private val serverRepository: MongoRepository<String, ServerEntity>,
    private val redis: RedisAPI
) : Route {

    override fun handle(request: Request, response: Response): Any {
        try {
            if (request.requestMethod() == "PUT") {
                val server = gson.fromJson(request.body(), ServerEntity::class.java)
                if (serverRepository.findById(server.id) != null) {
                    response.status(400)
                    return JsonObject().apply {
                        addProperty("message", "Server with this ID already exists!")
                    }
                }

                this.redis.publishAsync(
                    Shared.SERVER_CREATE_TOPIC,
                    CreateServerPacket(
                        server
                    )
                )

                response.status(200)
                return gson.toJson(JsonObject().apply {
                    addProperty("message", "Server has been created!")
                })
            } else {
                val servers = serverRepository.findAll()

                response.status(200)
                return gson.toJson(servers)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            response.status(500)
            return JsonObject().apply {
                addProperty("message", "An error occurred while processing the request!")
            }
        }
    }
}