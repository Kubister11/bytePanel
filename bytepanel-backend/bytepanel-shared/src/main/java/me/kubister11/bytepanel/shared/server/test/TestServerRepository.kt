package me.kubister11.bytepanel.shared.server.test

import com.google.gson.Gson
import me.kubister11.bytepanel.shared.database.MongoDB
import me.kubister11.bytepanel.shared.repository.MongoRepository
import me.kubister11.bytepanel.shared.server.Server
import java.util.*

class TestServerRepository : MongoRepository<String, Server> {

    val servers = mutableListOf(
        Server(
            UUID.randomUUID().toString(),
            "BOXPVP_1",
            100.0,
            200.0,
        ),
        Server(
            UUID.randomUUID().toString(),
            "BOXPVP_2",
            100.0,
            200.0,
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