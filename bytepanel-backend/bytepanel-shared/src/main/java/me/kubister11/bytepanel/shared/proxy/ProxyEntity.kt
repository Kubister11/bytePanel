package me.kubister11.bytepanel.shared.proxy

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProxyEntity(
    @Expose @SerializedName("_id") val id: String,

    @Expose val name: String,
    @Expose val hostname: Int,

    @Expose val endpoints: Map<Int, Endpoint>,
) {
    data class Endpoint(
        @Expose val ip: String,
        @Expose val port: Int,
        @Expose val protocol: Protocol
    )

    enum class Protocol {
        TCP,
        TCP_MINECRAFT,
    }
}