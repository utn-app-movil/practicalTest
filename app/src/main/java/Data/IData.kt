package Data

import Entity.Course
import Entity.Inscription
import Entity.Person
import Entity.Schedule
import Entity.Training

interface IData {
    //Course
    fun addCourse (course: Course)
    fun updateCourse (course: Course)
    fun removeCourse (id: String)
    fun getAllCourse(): List<Course>
    fun getByIdCourse(id: String): Course?
    fun getByCourseName(courseName: String): Course?

    //Inscription
    fun addInscription (inscription: Inscription)
    fun updateInscription (inscription: Inscription)
    fun removeInscription (id: String)
    fun getAllInscription(): List<Inscription>
    fun getByIdInscription(id: String): Inscription?

    //Schedule
    fun addSchedule (schedule: Schedule)
    fun updateSchedule (schedule: Schedule)
    fun removeSchedule (id: String)
    fun getAllSchedule(): List<Schedule>
    fun getByIdSchedule(id: String): Schedule?
    fun getByScheduleName(scheduleName: String): Schedule?

    //Training
    fun addTraining (training: Training)
    fun updateTraining (training: Training)
    fun removeTraining (id: String)
    fun getAllTraining(): List<Training>
    fun getByIdTraining(id: String): Training?
    fun getByTrainingName(training: String): Training?
}