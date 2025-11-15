package Data

import Entity.Sponsor

object MemoryDataManagerSponsor: IDataManagerSponsor {

    private var sponsorList = mutableListOf<Sponsor>()
    override fun add( sponsor: Sponsor) {
        sponsorList.add(sponsor)
    }
    override fun remove(id: String) {
        sponsorList.removeIf {it.id.trim()==id.trim()}
    }

    override fun update(sponsor: Sponsor) {
        remove(sponsor.id)
        add(sponsor)
    }

    override fun getAll() = sponsorList

    override fun getById(id: String): Sponsor? {
        try {
            val result = sponsorList.filter {it.id.trim()==id.trim()}
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

    override fun getByName(name: String): Sponsor? {
        try {
            val result = sponsorList.filter {it.nameSponsor.trim()==name.trim()}
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }
}