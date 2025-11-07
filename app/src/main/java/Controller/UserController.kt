package Controller

import Data.IDataManagerUser
import Data.MemoryDataManagerUser
import Entity.User
import android.content.Context
import cr.ac.utn.practicaltest.R

class UserController {
    private var dataManager: IDataManagerUser = MemoryDataManagerUser
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addUser(user: User){
        try {
            dataManager.add(user)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateUser(user: User){
        try {
            dataManager.update(user)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): User?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun getAll(): List<User> {
        try {
            return dataManager.getAll()
        } catch (e: Exception) {
            throw Exception(context
                .getString(R.string.ErrorMsgGetAll))
        }
    }

    fun removeUser(id: String){
        try{
            val result = dataManager.getById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgRemove))
        }
    }
}