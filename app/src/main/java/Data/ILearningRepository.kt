package Data

import Entity.LearningClass

interface ILearningRepository {
    fun getAll(): List<LearningClass>
    fun getById(id: Int): LearningClass?
    fun add(item: LearningClass): LearningClass
    fun update(item: LearningClass): Boolean
    fun remove(id: Int): Boolean
    fun existsByTitleIgnoreCase(title: String, ignoreId: Int? = null): Boolean
}
