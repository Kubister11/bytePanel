package me.kubister11.bytepanel.shared.server.test

import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import java.util.*

class TestServerRepository : MongoRepository<String, Server> {

    val servers = mutableListOf(
        Server(
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

    override fun findAll(): Collection<Server> {
        return servers
    }

    override fun delete(id: String) {
        servers.removeAll { it.id == id }
    }

    override fun update(id: String, value: Server) {
        servers.removeAll { it.id == id }
        servers.add(value)
    }

    override fun insert(value: Server) {
        servers.add(value)
    }

    override fun findById(id: String): Server? {
        return servers.firstOrNull { it.id == id }
    }
}