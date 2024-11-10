package me.kubister11.bytepanel.wings.listener

import me.kubister11.bytepanel.shared.packets.ConsoleCommandPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity
import me.kubister11.bytepanel.wings.repository.DockerContainerLocalRepository
import org.redisson.api.listener.MessageListener

class ServerCommandPacketListener(
    private val serverRepository: MongoRepository<String, ServerEntity>,
    private val wingsId: String,
    private val containerRepository: DockerContainerLocalRepository
) : MessageListener<ConsoleCommandPacket> {

    override fun onMessage(sequence: CharSequence, packet: ConsoleCommandPacket) {
        val server = this.serverRepository.findById(packet.serverId)

        if (server == null) {
            println("Cannot find server with id: ${packet.serverId}")
            return
        }

        if (server.wingsId != wingsId) {
            println("Server with id ${server.id} is not managed by this instance of Wings!")
            return
        }

        val container = this.containerRepository.findById(server.containerId ?: error("Container not found!")) ?: return
        container.executeCommand(packet.payload)
    }

}