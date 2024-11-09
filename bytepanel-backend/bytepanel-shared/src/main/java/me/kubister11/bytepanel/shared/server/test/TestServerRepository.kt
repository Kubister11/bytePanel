package me.kubister11.bytepanel.shared.server.test

import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.ServerEntity

class TestServerRepository : MongoRepository<String, ServerEntity> {

    val servers = mutableListOf(
        ServerEntity(
            "AAAAAAAAAAAAAAAABBBBBBBBBBBBBCCCCCCCCCCCDDDDDDDDDDDD",
            "BOXPVP_1",
            "test-minecraft",
            100,
            200,
            1024 * 10,
            "AAAAAAAAAAAAAAAABBBBBBBBBBBBBCCCCCCCCCCCDDDDDDDDDDDD",
            "stop",
            listOf(25565),
            ""
        )
    )

    override fun findAll(): Collection<ServerEntity> {
        return servers
    }

    override fun delete(id: String) {
        servers.removeAll { it.id == id }
    }

    override fun update(id: String, value: ServerEntity) {
        servers.removeAll { it.id == id }
        servers.add(value)
    }

    override fun insert(value: ServerEntity) {
        servers.add(value)
    }

    override fun findById(id: String): ServerEntity? {
        return servers.firstOrNull { it.id == id }
    }
}