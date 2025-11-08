package Controller

import Data.IDataManagerBooking
import Data.MemoryDataManagerBooking
import Entity.Booking
import android.content.Context
import cr.ac.utn.practicaltest.R

class BookingController {

    private var dataManager: IDataManagerBooking = MemoryDataManagerBooking
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addBooking(booking: Booking){
        try {
            dataManager.add(booking)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateBooking(booking: Booking){
        try {
            dataManager.update(booking)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Booking?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeBooking(id: String){
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