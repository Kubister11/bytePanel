package me.kubister11.bytepanel.shared.packets

data class ConsoleCommandPacket(
    val wingsId: String,
    val serverId: String,
    val payload: String,
)