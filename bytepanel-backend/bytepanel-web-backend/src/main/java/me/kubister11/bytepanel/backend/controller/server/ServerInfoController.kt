package me.kubister11.bytepanel.backend.controller.server

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import spark.Request
import spark.Response
import spark.Route

class ServerInfoController(
    private val gson: Gson,
    private val serverRepository: MongoRepository<String, Server>
) : Route {

    override fun handle(request: Request, response: Response): Any {
        val body = gson.fromJson(request.body(), JsonObject::class.java)
        val server = serverRepository.findById(body.get("id").asString)

        if (server == null) {
            response.status(400)
            return JsonObject().apply {
                addProperty("message", "Server with this ID does not exist!")
            }
        }


        response.status(200)
        return gson.toJson(server)
    }
}