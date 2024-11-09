package me.kubister11.bytepanel.wings.container

import me.kubister11.bytepanel.shared.server.ServerEntity

data class DockerServer(
    val server: ServerEntity,
    val container: DockerContainer,
)
