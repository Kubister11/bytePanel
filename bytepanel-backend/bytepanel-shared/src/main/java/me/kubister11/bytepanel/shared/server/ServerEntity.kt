package me.kubister11.bytepanel.shared.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerEntity(
    @Expose @SerializedName("_id") val id: String,

    @Expose val name: String,
    @Expose val dockerImage: String,
    @Expose val cpuLoadMax: Long,
    @Expose val memoryUsageMax: Long,
    @Expose val diskUsageMax: Long,

    @Expose val startCommand: String,
    @Expose val stopCommand: String,

    @Expose val exposedPorts: List<Int>,

    @Expose val wingsId: String
) {
    @Expose var containerId: String? = null

    var cpuLoad: Double = 0.0
    var memoryUsage: Long = 0L
    var diskUsage: Long = 0

    var state: ServerState = ServerState.OFFLINE
}
