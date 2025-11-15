package Controller

import Data.ITrainingDataManager
import Data.TrainingMemoryDataManager
import Entity.Training

class TrainingController {
    private val dataManager: ITrainingDataManager = TrainingMemoryDataManager()

    fun addTraining(training: Training) {
        dataManager.add(training)
    }

    fun updateTraining(training: Training) {
        dataManager.update(training)
    }

    fun deleteTraining(id: String) {
        dataManager.remove(id)
    }

    fun getTraining(id: String): Training? {
        return dataManager.getById(id)
    }

    fun getAllTrainings(): List<Training> {
        return dataManager.getAll()
    }
}