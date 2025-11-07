package Controller

import Data.IData
import Data.MemoryData
import Entity.Course
import android.content.Context
import cr.ac.utn.practicaltest.R

class CourseController {

    private var dataManager: IData = MemoryData
    private var context: Context

        constructor(context: Context){
        this.context=context
    }

    fun addCourse(course: Course){
        try {
            dataManager.addCourse(course)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateCourse(course: Course){
        try {
            dataManager.updateCourse(course)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getByIdCourse(id: String): Course?{
        try {
            return dataManager.getByIdCourse(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByCourseName(CourseName: String): Course?{
        try {
            return dataManager.
            getByCourseName(CourseName)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeCourse(id: String){
        try{
            val result = dataManager.getByIdCourse(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.removeCourse(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }

}