package Data

import Entity.Training

class TrainingMemoryDataManager : ITrainingDataManager {
    private val trainings = mutableListOf<Training>()

    override fun add(training: Training) {
        trainings.add(training)
    }

    override fun update(training: Training) {
        val index = trainings.indexOfFirst { it.id_training == training.id_training }
        if (index != -1) {
            trainings[index] = training
        }
    }

    override fun remove(id: String) {
        trainings.removeIf { it.id_training == id }
    }

    override fun getById(id: String): Training? {
        return trainings.find { it.id_training == id }
    }

    override fun getAll(): List<Training> {
        return trainings
    }
}