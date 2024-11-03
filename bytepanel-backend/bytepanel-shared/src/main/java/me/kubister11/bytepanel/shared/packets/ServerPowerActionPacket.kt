package me.kubister11.bytepanel.shared.packets

data class ServerPowerActionPacket(
    val serverId: String,
    val type: Type,
) {
    enum class Type {
        ON,
        OFF,
        RESTART
    }
}