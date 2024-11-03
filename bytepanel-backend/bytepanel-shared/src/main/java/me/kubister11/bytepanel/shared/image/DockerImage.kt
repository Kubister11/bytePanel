package me.kubister11.bytepanel.shared.image

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DockerImage(
    @Expose @SerializedName("_id") val name: String,
    @Expose val instructions: String
)