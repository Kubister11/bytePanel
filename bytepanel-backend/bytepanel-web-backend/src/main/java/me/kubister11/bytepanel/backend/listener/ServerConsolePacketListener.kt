package me.kubister11.bytepanel.backend.listener

import me.kubister11.bytepanel.shared.packets.ConsoleLogPacket
import me.kubister11.bytepanel.shared.socket.ConsoleWebSocketServer
import org.redisson.api.listener.MessageListener

class ServerConsolePacketListener(
    private val webSocketServer: ConsoleWebSocketServer,
) : MessageListener<ConsoleLogPacket> {

    override fun onMessage(sequence: CharSequence, packet: ConsoleLogPacket) {
        this.webSocketServer.log(packet.serverId, packet.content)
    }

}