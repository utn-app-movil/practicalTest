package Controller

import Data.ILearningRepository
import Entity.LearningClass
import Util.LearningValidator

class LearningController(private val repo: ILearningRepository) {

    fun list(): List<LearningClass> = repo.getAll()

    fun create(title: String, schedule: String, professor: String): Result<LearningClass> {
        if (!LearningValidator.isNotBlank(title))      return Result.failure(IllegalArgumentException("title"))
        if (!LearningValidator.isNotBlank(schedule))    return Result.failure(IllegalArgumentException("schedule"))
        if (!LearningValidator.isNotBlank(professor))   return Result.failure(IllegalArgumentException("professor"))
        if (repo.existsByTitleIgnoreCase(title))        return Result.failure(IllegalStateException("duplicate"))
        return Result.success(
            repo.add(LearningClass(title = title.trim(), schedule = schedule.trim(), professor = professor.trim()))
        )
    }

    fun update(id: Int, title: String, schedule: String, professor: String): Result<Boolean> {
        if (!LearningValidator.isNotBlank(title))      return Result.failure(IllegalArgumentException("title"))
        if (!LearningValidator.isNotBlank(schedule))    return Result.failure(IllegalArgumentException("schedule"))
        if (!LearningValidator.isNotBlank(professor))   return Result.failure(IllegalArgumentException("professor"))
        if (repo.existsByTitleIgnoreCase(title, ignoreId = id)) return Result.failure(IllegalStateException("duplicate"))
        return Result.success(repo.update(LearningClass(id = id, title = title.trim(), schedule = schedule.trim(), professor = professor.trim())))
    }

    fun delete(id: Int): Boolean = repo.remove(id)
}
