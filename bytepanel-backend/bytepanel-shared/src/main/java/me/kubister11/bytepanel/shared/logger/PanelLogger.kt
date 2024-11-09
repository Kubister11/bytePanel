package me.kubister11.bytepanel.shared.logger

import me.kubister11.bytepanel.shared.Shared
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.packets.ConsoleLogPacket

class PanelLogger(
    private val serverId: String,
    private val redis: RedisAPI,
    private val debug: Boolean = false
) {

    fun info(message: String) {
        val formatter = "[INFO] $message"
        this.publish(message)
        if (debug) println(formatter)
    }

    fun error(message: String) {
        val formatter = "[ERROR] $message"
        this.publish(message)
        if (debug) println(formatter)
    }

    fun warn(message: String) {
        val formatter = "[WARN] $message"
        this.publish(message)
        if (debug) println(formatter)
    }


    private fun publish(message: String) {
        this.redis.publishAsync(
            Shared.CONSOLE_TOPIC,
            ConsoleLogPacket(
                serverId,
                message
            )
        )
    }
}