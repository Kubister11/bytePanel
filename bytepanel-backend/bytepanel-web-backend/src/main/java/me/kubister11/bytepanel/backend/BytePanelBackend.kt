package me.kubister11.bytepanel.backend

import com.google.gson.GsonBuilder
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import me.kubister11.bytepanel.backend.controller.server.ServerInfoController
import me.kubister11.bytepanel.backend.controller.server.ServerListController
import me.kubister11.bytepanel.backend.controller.server.ServerPowerActionController
import me.kubister11.bytepanel.shared.Shared
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.image.ImageRepository
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity
import me.kubister11.bytepanel.shared.server.ServerRepository
import spark.Filter
import spark.Spark.*

class BytePanelBackend {


    private lateinit var redis: RedisAPI
    private lateinit var mongoDB: MongoDB

    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val exposeGson = GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private lateinit var serverRepository: MongoRepository<String, ServerEntity>
    private lateinit var imagesRepository: MongoRepository<String, DockerImage>

    private lateinit var serverListController: ServerListController

    fun start() {
        this.redis = RedisAPI(
            "83.168.108.123",
            20014,
            "@Py8i@iV4H96"
        )
        this.redis.connect()

        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("mongodb://admin:i9P65H5K8mIDkUPL@83.168.108.123:20015/"))
            .build()

        this.mongoDB = MongoDB(settings, "bytePanel-TEST")
        this.mongoDB.connect()

        this.serverRepository = ServerRepository(
            this.mongoDB,
            this.gson
        )

        this.imagesRepository = ImageRepository(
            this.mongoDB,
            this.gson
        )

        this.redis.registerTopic(Shared.CONSOLE_TOPIC)
        this.redis.registerTopic(Shared.POWER_ACTIONS_TOPIC)
        this.redis.registerTopic(Shared.CONTAINER_STATE_TOPIC)
        this.redis.registerTopic(Shared.SERVER_CREATE_TOPIC)


        port(5631)

        before(Filter { _, response ->
            response.header("Access-Control-Allow-Origin", "*")
            response.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS")
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, Accept")
        })

        options("/*") { _, response ->
            response.status(200)
            "OK"
        }

        this.serverListController = ServerListController(this.gson, this.serverRepository, this.redis)

        get("/api/server", ServerInfoController(this.gson, this.serverRepository))

        get("/api/servers", this.serverListController)
        put("/api/servers", this.serverListController)

        post("/api/server/power", ServerPowerActionController(this.gson, this.serverRepository, this.redis))
    }
}