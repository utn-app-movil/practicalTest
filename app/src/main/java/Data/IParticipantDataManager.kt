package Data

import Entity.Participant

interface IParticipantDataManager {

    fun add(participant: Participant): Boolean
    fun update(participant: Participant): Boolean
    fun remove(id: String): Boolean
    fun getAll(): List<Participant>
    fun getById(id: String): Participant?
    fun getByName(name: String): Participant?
}