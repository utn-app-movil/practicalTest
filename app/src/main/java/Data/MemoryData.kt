package Data

import Entity.Course
import Entity.Inscription
import Entity.Schedule
import Entity.Training

object MemoryData: IData {
    //Course
    private var CourseList      = mutableListOf<Course>()
    private var InscriptionList = mutableListOf<Inscription>()
    private var ScheduleList    = mutableListOf<Schedule>()
    private var TrainingList    = mutableListOf<Training>()
    override fun addCourse(course: Course) {
        CourseList.add(course)
    }

    override fun removeCourse(id: String) {
        CourseList.removeIf { it.idCourse.trim() == id.trim() }
    }

    override fun updateCourse(course: Course) {
        removeCourse(course.idCourse)
        addCourse(course)
    }

    override fun getAllCourse()= CourseList

    override fun getByIdCourse(id: String): Course? {
        val result = CourseList.
        filter { it.idCourse.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByCourseName(courseName: String): Course? {
        val result = CourseList.
        filter { it.Course_Name == courseName.trim()}
        return if(result.any()) result[0] else null
    }

    //Inscription
    override fun addInscription(inscription: Inscription) {
        InscriptionList.add(inscription)
    }

    override fun removeInscription(id: String) {
        InscriptionList.removeIf { it.idInscription.trim() == id.trim() }
    }

    override fun updateInscription(inscription: Inscription) {
        removeInscription(inscription.idInscription)
        addInscription(inscription)
    }

    override fun getAllInscription()= InscriptionList

    override fun getByIdInscription(id: String): Inscription? {
        val result = InscriptionList.
        filter { it.idInscription.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    //Schedule

    override fun addSchedule(schedule: Schedule) {
        ScheduleList.add(schedule)
    }

    override fun removeSchedule(id: String) {
        ScheduleList.removeIf { it.idSchedule.trim() == id.trim() }
    }

    override fun updateSchedule(schedule: Schedule) {
        removeSchedule(schedule.idSchedule)
        addSchedule(schedule)
    }

    override fun getAllSchedule()= ScheduleList

    override fun getByIdSchedule(id: String): Schedule? {
        val result = ScheduleList.
        filter { it.idSchedule.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByScheduleName(scheduleName: String): Schedule? {
        val result = ScheduleList.
        filter { it.Schedule_Name == scheduleName.trim()}
        return if(result.any()) result[0] else null
    }

    //Training

    override fun addTraining(training: Training) {
        TrainingList.add(training)
    }

    override fun removeTraining(id: String) {
        TrainingList.removeIf { it.idTraining.trim() == id.trim() }
    }

    override fun updateTraining(training: Training) {
        removeTraining(training.idTraining)
        addTraining(training)
    }

    override fun getAllTraining()= TrainingList

    override fun getByIdTraining(id: String): Training? {
        val result = TrainingList.
        filter { it.idTraining.trim() == id.trim()}
        return if(result.any()) result[0] else null
    }

    override fun getByTrainingName(trainingName: String): Training? {
        val result = TrainingList.
        filter { it.nameTraining == trainingName.trim()}
        return if(result.any()) result[0] else null
    }

}