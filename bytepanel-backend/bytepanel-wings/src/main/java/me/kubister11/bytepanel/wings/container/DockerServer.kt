package me.kubister11.bytepanel.wings.container

import me.kubister11.bytepanel.shared.server.Server

data class DockerServer(
    val server: Server,
    val container: DockerContainer,
)
