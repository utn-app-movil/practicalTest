package Data

import Entity.Participant

object MemoryParticipantDataManager: IParticipantDataManager {

    private val participants = mutableListOf<Participant>()

    override fun add(participant: Participant): Boolean {
        return try {
            participants.add(participant)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun update(participant: Participant): Boolean {
        val index = participants.indexOfFirst { it.Id == participant.Id }
        return if (index != -1) {
            participants[index] = participant
            true
        } else false
    }

    override fun remove(id: String): Boolean {
        return participants.removeIf { it.Id == id }
    }

    override fun getAll(): List<Participant> = participants

    override fun getById(id: String): Participant? =
        participants.firstOrNull { it.Id == id }

    override fun getByName(name: String): Participant? =
        participants.firstOrNull { it.Name.equals(name, ignoreCase = true) }
}