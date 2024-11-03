package me.kubister11.bytepanel.wings.listener

import me.kubister11.bytepanel.shared.Wings
import me.kubister11.bytepanel.shared.database.RedisAPI
import me.kubister11.bytepanel.shared.packets.ContainerStatePacket
import me.kubister11.bytepanel.shared.packets.ServerPowerActionPacket
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import me.kubister11.bytepanel.shared.server.ServerState
import me.kubister11.bytepanel.wings.repository.DockerContainerLocalRepository
import org.redisson.api.listener.MessageListener

class ServerPowerActionListener(
    private val serverRepository: MongoRepository<String, Server>,
    private val wingsId: String,
    private val containerRepository: DockerContainerLocalRepository,
    private val redis: RedisAPI
) : MessageListener<ServerPowerActionPacket> {

    override fun onMessage(sequence: CharSequence, packet: ServerPowerActionPacket) {
        println("Received power action packet!")
        val server = this.serverRepository.findById(packet.serverId)

        if (server == null) {
            println("Cannot find server with id: ${packet.serverId}")
            return
        }

        if (server.wingsId != wingsId) {
            println("Server with id ${server.id} is not managed by this instance of Wings!")
            return
        }

        println("P 1 ${server.containerId}")
        val container = this.containerRepository.findById(server.containerId ?: error("Container not found!")) ?: return
        println("P 2")
        when (packet.type) {
            ServerPowerActionPacket.Type.ON -> {
                this.redis.publishAsync(
                    Wings.CONTAINER_STATE_TOPIC,
                    ContainerStatePacket(
                        server.id,
                        ServerState.STARTING
                    )
                )

                println("Starting container...")
                container.start()
            }
            ServerPowerActionPacket.Type.OFF -> {
                this.redis.publishAsync(
                    Wings.CONTAINER_STATE_TOPIC,
                    ContainerStatePacket(
                        server.id,
                        ServerState.STOPPING
                    )
                )

                container.stop()
            }
            ServerPowerActionPacket.Type.RESTART -> {
                container.kill() //TODO: change to safe stop
                container.start()
            }
        }
    }

}