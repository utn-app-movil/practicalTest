package Data

import Entity.Vehicle

interface VehicleManager {
    fun add(vehicle: Vehicle)
    fun update(vehicle: Vehicle)
    fun remove(id: String)
    fun getAll(): List<Vehicle>
    fun getById(id: String): Vehicle?
    fun getByPlate(plate: String): Vehicle?
}

