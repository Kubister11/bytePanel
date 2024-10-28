package me.kubister11.bytepanel.shared

data class Server(
    val id: String,
    val name: String,

    val cpuLoad: Double,
    val cpuLoadMax: Double,

    val memoryUsage: Double,
    val memoryUsageMax: Double,

    val diskUsage: Int,

    val state: ServerState
)
