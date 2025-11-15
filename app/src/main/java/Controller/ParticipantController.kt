package Controller

import Data.MemoryParticipantDataManager
import Entity.Participant

class ParticipantController {

    private val participantData = MemoryParticipantDataManager

    fun addParticipant(participant: Participant): Boolean {
        if (participant.Name.isEmpty() || participant.Email.isEmpty()) {
            return false
        }
        return participantData.add(participant)
    }

    fun updateParticipant(participant: Participant): Boolean {
        return participantData.update(participant)
    }

    fun deleteParticipant(id: String): Boolean {
        return participantData.remove(id)
    }

    fun getAllParticipants(): List<Participant> {
        return participantData.getAll()
    }

    fun getParticipantById(id: String): Participant? {
        return participantData.getById(id)
    }

    fun getParticipantByName(name: String): Participant? {
        return participantData.getByName(name)
    }
}
