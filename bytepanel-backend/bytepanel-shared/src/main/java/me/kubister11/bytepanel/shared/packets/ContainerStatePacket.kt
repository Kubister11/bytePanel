package me.kubister11.bytepanel.shared.packets

import me.kubister11.bytepanel.shared.server.ServerState

data class ContainerStatePacket(
    val serverId: String,
    val state: ServerState
)