package me.kubister11.bytepanel.shared.server

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Server(
    @SerializedName("_id") val id: String,

    @Expose val name: String,
    @Expose val cpuLoadMax: Int,
    @Expose val memoryUsageMax: Long,
    @Expose val diskUsageMax: Long,
    @Expose val wingsId: String,
    @Expose val containerId: String,
) {
    val cpuLoad: Double = 0.0
    val memoryUsage: Long = 0L
    val diskUsage: Long = 0
    val state: ServerState = ServerState.OFFLINE
}
