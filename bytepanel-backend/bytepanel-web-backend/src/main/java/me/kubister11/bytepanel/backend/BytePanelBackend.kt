package me.kubister11.bytepanel.backend

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.kubister11.bytepanel.backend.controller.server.ServerListController
import spark.Spark.get
import spark.Spark.port

class BytePanelBackend {

    private val gson = GsonBuilder().setPrettyPrinting().create()

    fun start() {
        port(5631)

        get("/api/servers", ServerListController(gson))
    }
}