package Data

import Entity.Training

interface ITrainingDataManager {
    fun add(training: Training)
    fun update(training: Training)
    fun remove(id: String)
    fun getById(id: String): Training?
    fun getAll(): List<Training>
}