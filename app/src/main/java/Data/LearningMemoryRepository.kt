package Data

import Entity.LearningClass

class LearningMemoryRepository : ILearningRepository {

    private val data = mutableListOf(
        LearningClass(id = 1, title = "Fútbol",   schedule = "Lun 6–8 pm",  professor = "Carlos Jiménez"),
        LearningClass(id = 2, title = "Natación", schedule = "Mié 7–9 pm",  professor = "María Pérez")
    )
    private var nextId = (data.maxOfOrNull { it.id } ?: 0) + 1

    override fun getAll(): List<LearningClass> = data.toList()

    override fun getById(id: Int): LearningClass? = data.firstOrNull { it.id == id }

    override fun add(item: LearningClass): LearningClass {
        val withId = item.copy(id = nextId++)
        data.add(withId)
        return withId
    }

    override fun update(item: LearningClass): Boolean {
        val idx = data.indexOfFirst { it.id == item.id }
        if (idx == -1) return false
        data[idx] = item
        return true
    }

    override fun remove(id: Int): Boolean = data.removeIf { it.id == id }

    override fun existsByTitleIgnoreCase(title: String, ignoreId: Int?): Boolean {
        val t = title.trim().lowercase()
        return data.any { it.title.trim().lowercase() == t && (ignoreId == null || it.id != ignoreId) }
    }
}
