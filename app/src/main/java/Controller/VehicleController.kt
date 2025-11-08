package Controller

import Data.IVehicleManager
import Data.VehicleDataManager
import Entity.Vehicle
import android.content.Context
import cr.ac.utn.practicaltest.R

class VehicleController {

    private var dataVehicle: IVehicleManager = VehicleDataManager

    private var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addVehicle(vehicle: Vehicle){
        try {
            dataVehicle.add(vehicle)
        } catch (e: Exception){
            throw Exception("String")
        }
    }

    fun updateVehicle(vehicle: Vehicle){
        try {
            dataVehicle.update(vehicle)
        } catch (e: Exception){
            throw Exception("String")
        }
    }

    fun getById(id: String): Vehicle?{
        try {
            return dataVehicle.getById(id)
        } catch (e: Exception){
            throw Exception("String")
        }
    }

    fun removeVehicle(id: String){
        try{
            val result = dataVehicle.getById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.MsgDataNoFound))
            }
            dataVehicle.remove(id)
        }catch (e: Exception){
            throw Exception("String")
        }
    }
}