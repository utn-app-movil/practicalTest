package Data

import Entity.Sponsor

interface IDataManagerSponsor {
    fun add (sponsor: Sponsor)
    fun update (sponsor: Sponsor)
    fun remove (id: String)
    fun getAll(): List<Sponsor>
    fun getById(id: String): Sponsor?
    fun getByName(name: String): Sponsor?
}