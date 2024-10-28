package me.kubister11.bytepanel.backend.controller.server

import com.google.gson.Gson
import me.kubister11.bytepanel.shared.Server
import me.kubister11.bytepanel.shared.ServerState
import spark.Request
import spark.Response
import spark.Route
import java.util.*

class ServerListController(
    val gson: Gson,
) : Route {

    val servers = listOf(
        Server(
            UUID.randomUUID().toString(),
            "BOXPVP_1",
            100.0,
            200.0,
            4.53,
            7.0,
            60,
            ServerState.ONLINE
        ),
        Server(
            UUID.randomUUID().toString(),
            "BOXPVP_2",
            100.0,
            200.0,
            4.53,
            7.0,
            60,
            ServerState.ONLINE
        )
    )

    override fun handle(request: Request, response: Response): Any {
        response.status(200)
        return gson.toJson(servers)
    }
}