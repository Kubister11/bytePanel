package me.kubister11.bytepanel.backend

import com.google.gson.GsonBuilder
import me.kubister11.bytepanel.backend.controller.server.ServerInfoController
import me.kubister11.bytepanel.backend.controller.server.ServerListController
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.shared.server.test.TestServerRepository
import spark.Filter
import spark.Spark.*

class BytePanelBackend {

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val exposeGson = GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private lateinit var serverRepository: MongoRepository<String, Server>

    private lateinit var serverListController: ServerListController

    fun start() {
        this.serverRepository = TestServerRepository() //TEST

        port(5631)

        after(Filter { _, response ->
            response.header("Access-Control-Allow-Origin", "*")
            response.header("Access-Control-Allow-Methods", "GET")
        })

        this.serverListController = ServerListController(gson, serverRepository)

        get("/api/server", ServerInfoController(gson, serverRepository))

        get("/api/servers", this.serverListController)
        put("/api/servers", this.serverListController)
    }
}