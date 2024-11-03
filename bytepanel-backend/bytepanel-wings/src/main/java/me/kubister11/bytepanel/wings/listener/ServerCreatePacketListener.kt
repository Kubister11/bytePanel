package me.kubister11.bytepanel.wings.listener

import me.kubister11.bytepanel.shared.image.DockerImage
import me.kubister11.bytepanel.shared.packets.CreateServerPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.wings.factory.DockerContainerFactory
import org.redisson.api.listener.MessageListener

class ServerCreatePacketListener(
    private val imageRepository: MongoRepository<String, DockerImage>,
    private val serverService: DockerContainerFactory,
    private val wingsId: String
) : MessageListener<CreateServerPacket> {

    override fun onMessage(sequence: CharSequence, packet: CreateServerPacket) {
        if (packet.server.wingsId != this.wingsId) return

        val image = this.imageRepository.findById(packet.server.dockerImage)

        if (image == null) {
            println("Cannot find image with id: ${packet.server.dockerImage}")
            return
        }

        println("Creating server.. (name: ${packet.server.name})")
        this.serverService.createContainer(packet.server, image).thenRun {
            println("Server created! (name: ${packet.server.name})")
        }
    }

}