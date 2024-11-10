package me.kubister11.bytepanel.shared.socket

import me.kubister11.bytepanel.shared.Shared
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.packets.ConsoleCommandPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class ConsoleWebSocketServer(
    port: Int,
    private val redis: RedisAPI,
    private val serverRepository: MongoRepository<String, ServerEntity>
) : WebSocketServer(InetSocketAddress(port)) {

    private val connectedClients = mutableMapOf<WebSocket, ServerEntity?>()
    private val logs = mutableMapOf<String, MutableList<String>>()

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        connectedClients[conn] = null
        println("new connection")
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        connectedClients.remove(conn)
    }

    override fun onMessage(conn: WebSocket, message: String) {
        var server = connectedClients[conn]
        println("message")

        if (server == null) {
            server = serverRepository.findById(message) ?: return
            connectedClients[conn] = server

            var logs = logs[message]
            if (logs == null) {
                logs = mutableListOf()
                this.logs[message] = logs
            } else if (logs.size > 100) {
                logs = logs.subList(100, logs.size)
            }

            println("sent logs")
            logs.forEach {
                conn.send(it)
            }

            return
        }

        redis.publishAsync(
            Shared.SEND_COMMAND_TOPIC,
            ConsoleCommandPacket(
                server.wingsId,
                server.id,
                message,
            )
        )
    }

    override fun onError(conn: WebSocket, ex: Exception) {
        ex.printStackTrace()
    }

    override fun onStart() {
    }

    fun log(serverId: String, command: String) {
        println("logging...")
        connectedClients.filterValues { it?.id == serverId }.forEach { (conn, _) ->
            conn.send(command)
        }
        logs[serverId]?.add(command)
        println("log added")
    }

}