package me.kubister11.bytepanel.backend.controller.server

import com.google.gson.Gson
import com.google.gson.JsonObject
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.shared.server.ServerState
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class ServerListController(
    private val gson: Gson,
    private val serverRepository: MongoRepository<String, Server>
) : Route {

    override fun handle(request: Request, response: Response): Any {
        try {
            if (request.requestMethod() == "PUT") {
                val server = gson.fromJson(request.body(), Server::class.java)
                if (serverRepository.findById(server.id) != null) {
                    response.status(400)
                    return JsonObject().apply {
                        addProperty("message", "Server with this ID already exists!")
                    }
                }

                serverRepository.insert(server)
            }

            val servers = serverRepository.findAll()

            response.status(200)
            return gson.toJson(servers)
        } catch (exception: Exception) {
            exception.printStackTrace()
            response.status(500)
            return JsonObject().apply {
                addProperty("message", "An error occurred while processing the request!")
            }
        }
    }
}