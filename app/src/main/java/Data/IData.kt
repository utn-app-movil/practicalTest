package Data

import Entity.Inscription
import Entity.Person

interface IData {
    //Inscription
    fun addInscription (inscription: Inscription)
    fun updateInscription (inscription: Inscription)
    fun removeInscription (id: String)
    fun getAllInscription(): List<Inscription>
    fun getByIdInscription(id: String): Inscription?

}