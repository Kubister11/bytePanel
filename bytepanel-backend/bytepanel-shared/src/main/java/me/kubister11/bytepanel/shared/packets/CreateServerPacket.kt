package me.kubister11.bytepanel.shared.packets

import me.kubister11.bytepanel.shared.server.Server

data class CreateServerPacket(
    val server: Server
)