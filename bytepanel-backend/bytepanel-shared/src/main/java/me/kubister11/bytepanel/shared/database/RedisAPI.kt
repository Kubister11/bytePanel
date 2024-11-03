package me.kubister11.bytepanel.shared.database

import org.redisson.Redisson
import org.redisson.api.RFuture
import org.redisson.api.RMap
import org.redisson.api.RTopic
import org.redisson.api.RedissonClient
import org.redisson.api.listener.MessageListener
import org.redisson.client.codec.Codec
import org.redisson.config.Config
import org.redisson.misc.CompletableFutureWrapper

class RedisAPI(
    private val host: String,
    private val port: Int,
    private val password: String?,
    private val codec: Codec? = null
) {
    lateinit var client: RedissonClient
    private val maps: MutableMap<Class<*>, RMap<String, *>> = HashMap()
    private val topics: MutableMap<String, RTopic> = HashMap()

    fun connect(): Boolean {
        val config = Config()

        config.useSingleServer()
            .setAddress("redis://$host:$port")
            .setPassword(password)

        if (codec != null) config.setCodec(codec)

        try {
            client = Redisson.create(config)
            return true
        } catch (exception: Exception) {
            exception.printStackTrace()
            return false
        }
    }

    fun <DATA> registerMap(name: String, data: Class<DATA>) : RMap<String, DATA> {
        if (!::client.isInitialized) error("Redis client not initialized")

        val map = client.getMap<String, DATA>(name)
        maps[data] = map
        return map
    }

    fun <DATA> getMap(key: Class<DATA>): RMap<String, DATA> {
        if (!::client.isInitialized) error("Redis client not initialized")

        val remoteMap = maps[key] ?: throw IllegalStateException("No map registered for $key")
        return remoteMap as RMap<String, DATA>
    }

    fun isMapRegistered(key: Class<*>): Boolean {
        if (!::client.isInitialized) return false

        return maps.containsKey(key)
    }

    fun registerTopic(topic: String): String {
        if (!::client.isInitialized) return topic

        topics[topic] = client.getTopic(topic)
        return topic
    }

    fun <DATA> registerTopicListener(topic: String, clazz: Class<DATA>, listener: MessageListener<DATA>) {
        if (!::client.isInitialized) error("Redis client not initialized")

        val rTopic = topics[topic] ?: throw IllegalStateException("No topic registered for $topic")
        rTopic.addListener(clazz, listener)
    }

    fun publish(topic: String, data: Any) {
        if (!::client.isInitialized) return

        val rTopic = topics[topic] ?: throw IllegalStateException("No topic registered for $topic")
        rTopic.publish(data)
    }

    fun publishAsync(topic: String, data: Any): RFuture<Long> {
        if (!::client.isInitialized) return CompletableFutureWrapper(0L)

        val rTopic = topics[topic] ?: throw IllegalStateException("No topic registered for $topic")
        return rTopic.publishAsync(data)
    }
}
