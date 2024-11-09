package me.kubister11.bytepanel.wings

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import com.google.gson.GsonBuilder
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import me.kubister11.bytepanel.shared.Shared
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.image.ImageRepository
import me.kubister11.bytepanel.shared.packets.CreateServerPacket
import me.kubister11.bytepanel.shared.packets.ServerPowerActionPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity
import me.kubister11.bytepanel.shared.server.ServerRepository
import me.kubister11.bytepanel.wings.listener.ServerCreatePacketListener
import me.kubister11.bytepanel.wings.listener.ServerPowerActionListener
import me.kubister11.bytepanel.wings.repository.DockerContainerLocalRepository
import me.kubister11.bytepanel.wings.service.DockerImageService
import me.kubister11.bytepanel.wings.factory.DockerContainerFactory


class BytePanelWings {
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    private val exposeGson = GsonBuilder()
        .setPrettyPrinting()
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    private val dockerClient = createDockerClient()

    private lateinit var redis: RedisAPI
    private lateinit var mongoDB: MongoDB

    private lateinit var wingsId: String
    private lateinit var dockerContainerFactory: DockerContainerFactory
    private lateinit var imageService: DockerImageService
    private lateinit var dockerContainerLocalRepository: DockerContainerLocalRepository

    private lateinit var serverRepository: MongoRepository<String, ServerEntity>
    private lateinit var imageRepository: MongoRepository<String, DockerImage>

    fun start() {
        this.wingsId = "MIZCIgZy3UnvV4Yo2TP3pLAmvKU7Kd5nLjLfgb4bJJv6DCoODG"
        println("Wings ID: $wingsId")

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
            this.exposeGson
        )
        this.imageRepository = ImageRepository(
            this.mongoDB,
            this.gson
        )

        this.imageService = DockerImageService(this.dockerClient)

        this.dockerContainerLocalRepository = DockerContainerLocalRepository(this.imageService)

        this.dockerContainerFactory = DockerContainerFactory(
            this.dockerClient,
            this.serverRepository,
            this.dockerContainerLocalRepository,
            this.redis
        )

        this.serverRepository.findAll().forEach {
            if (it.wingsId != this.wingsId) return@forEach

            println("Server with id ${it.id} has been loaded! (containerId: ${it.containerId})")
            this.dockerContainerFactory.createContainer(
                it,
                this.imageRepository.findById(it.dockerImage)!!,
                false
            ).join()
        }

        this.redis.registerTopic(Shared.CONSOLE_TOPIC)
        this.redis.registerTopic(Shared.POWER_ACTIONS_TOPIC)
        this.redis.registerTopic(Shared.CONTAINER_STATE_TOPIC)
        this.redis.registerTopic(Shared.SERVER_CREATE_TOPIC)

        this.redis.registerTopicListener(
            Shared.POWER_ACTIONS_TOPIC,
            ServerPowerActionPacket::class.java,
            ServerPowerActionListener(
                this.serverRepository,
                this.wingsId,
                this.dockerContainerLocalRepository,
                this.redis
            )
        )

        this.redis.registerTopicListener(
            Shared.SERVER_CREATE_TOPIC,
            CreateServerPacket::class.java,
            ServerCreatePacketListener(
                this.imageRepository,
                this.dockerContainerFactory,
                this.wingsId
            )
        )


        println("BytePanel Wings started!")
    }

    private fun createDockerClient(): DockerClient {
        val config =
            DefaultDockerClientConfig.createDefaultConfigBuilder().build()
        val dockerHost = ApacheDockerHttpClient.Builder().dockerHost(config.dockerHost).build()

        return DockerClientImpl.getInstance(
            config, dockerHost
        )
    }

}