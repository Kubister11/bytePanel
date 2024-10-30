package me.kubister11.bytepanel.shared.repository

interface MongoRepository<KEY, VALUE> {
    fun findAll(): Collection<VALUE>
    fun findById(id: KEY): VALUE?

    fun insert(value: VALUE)
    fun update(id: String, value: VALUE)
    fun delete(id: KEY)
}