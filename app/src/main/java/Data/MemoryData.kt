package Data

import Entity.Inscription
import Entity.Person

object MemoryData: IData {
    private  var InscriptionList = mutableListOf<Inscription>()
    //Inscription
    override fun addInscription(inscription: Inscription) {
        InscriptionList.add(inscription)
    }

    override fun removeInscription(id: String) {
        InscriptionList.removeIf { it.idInscription.trim() == id.trim() }
    }

    override fun updateInscription(inscription: Inscription) {
        removeInscription(inscription.idInscription)
        addInscription(inscription)
    }

    override fun getAllInscription()= InscriptionList

    override fun getByIdInscription(id: String): Inscription? {
        val result = InscriptionList.
        filter { it.idInscription.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

}