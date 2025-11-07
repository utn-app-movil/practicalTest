package Controller

import Data.IDataManager
import Data.MemoryDataManager
import Data.MemoryVehicleManager
import Data.VehicleManager
import Entity.Vehicle
import android.content.Context
import cr.ac.utn.practicaltest.R

class VehicleController {

    private var dataManager: VehicleManager = MemoryVehicleManager
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun addVehicle(vehicle: Vehicle) {
        try {
            dataManager.add(vehicle)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        try {
            dataManager.update(vehicle)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(id: String): Vehicle? {
        try {
            return dataManager.getById(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByPlate(plate: String): Vehicle? {
        try {
            return dataManager.getByPlate(plate)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    fun removeVehicle(id: String) {
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.MsgDataNoFound))
            }
            dataManager.remove(id)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }
}
