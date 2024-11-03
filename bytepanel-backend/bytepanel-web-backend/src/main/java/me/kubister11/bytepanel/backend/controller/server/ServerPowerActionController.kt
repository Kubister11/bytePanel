package me.kubister11.bytepanel.backend.controller.server

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.kubister11.bytepanel.shared.Wings
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.packets.ServerPowerActionPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import spark.Request
import spark.Response
import spark.Route

class ServerPowerActionController(
    private val gson: Gson,
    private val serverRepository: MongoRepository<String, Server>,
    private val redis: RedisAPI
) : Route {

    companion object {
        val INVALID_REQUEST = JsonObject().apply {
            addProperty("message", "Invalid request!")
        }
    }

    override fun handle(request: Request, response: Response): Any {
        try {
            response.type("application/json")

            println(request.body())
            val body = gson.fromJson(request.body(), JsonObject::class.java)

            val server = serverRepository.findById(body.get("id").asString)
            if (server == null) {
                response.status(400)
                return this.gson.toJson(INVALID_REQUEST)
            }

            val actionString = body.get("action")
            if (actionString == null) {
                response.status(400)
                return this.gson.toJson(INVALID_REQUEST)
            }

            val action = try {
                ServerPowerActionPacket.Type.valueOf(actionString.asString)
            } catch (exception: Exception) {
                response.status(400)
                return this.gson.toJson(INVALID_REQUEST)
            }


            this.redis.publishAsync(
                Wings.POWER_ACTIONS_TOPIC,
                ServerPowerActionPacket(
                    server.id,
                    action
                )
            )

            response.status(200)
            return gson.toJson(server)
        } catch (exception: Exception) {
            exception.printStackTrace()
            response.status(500)
            return gson.toJson(JsonObject().apply {
                addProperty("message", "An error occurred while processing the request!")
            })
        }
    }
}