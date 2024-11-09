package me.kubister11.bytepanel.shared.packets

import me.kubister11.bytepanel.shared.server.ServerEntity

data class CreateServerPacket(
    val server: ServerEntity
)