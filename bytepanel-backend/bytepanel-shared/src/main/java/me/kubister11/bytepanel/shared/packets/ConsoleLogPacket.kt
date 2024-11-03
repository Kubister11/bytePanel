package me.kubister11.bytepanel.shared.packets

data class ConsoleLogPacket(
    val serverId: String,
    val content: String
)