package me.kubister11.bytepanel.backend

import com.google.gson.GsonBuilder
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import me.kubister11.bytepanel.backend.controller.server.ServerInfoController
import me.kubister11.bytepanel.backend.controller.server.ServerListController
import me.kubister11.bytepanel.backend.controller.server.ServerPowerActionController
import me.kubister11.bytepanel.shared.Wings
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.image.ImageRepository
import me.kubister11.bytepanel.shared.packets.CreateServerPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.shared.server.ServerRepository
import me.kubister11.bytepanel.shared.server.test.TestServerRepository
import spark.Filter
import spark.Spark.*
import java.util.*

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

    private lateinit var serverRepository: MongoRepository<String, Server>
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

        this.redis.registerTopic(Wings.CONSOLE_TOPIC)
        this.redis.registerTopic(Wings.POWER_ACTIONS_TOPIC)
        this.redis.registerTopic(Wings.CONTAINER_STATE_TOPIC)
        this.redis.registerTopic(Wings.SERVER_CREATE_TOPIC)


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

        this.serverListController = ServerListController(gson, serverRepository)

        get("/api/server", ServerInfoController(gson, serverRepository))

        get("/api/servers", this.serverListController)
        put("/api/servers", this.serverListController)

        post("/api/server/power", ServerPowerActionController(gson, serverRepository, redis))
    }
}