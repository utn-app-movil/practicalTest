package Data

import Entity.Vehicle

object MemoryVehicleManager : VehicleManager {
    private var vehicleList = mutableListOf<Vehicle>()

    override fun add(vehicle: Vehicle) {
        vehicleList.add(vehicle)
    }

    override fun remove(id: String) {
        vehicleList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(vehicle: Vehicle) {
        remove(vehicle.ID)
        add(vehicle)
    }

    override fun getAll() = vehicleList

    override fun getById(id: String): Vehicle? {
        val result = vehicleList.filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun getByPlate(plateNumber: String): Vehicle? {
        val result = vehicleList.filter { it.PlateNumber.trim() == plateNumber.trim() }
        return if (result.any()) result[0] else null
    }
}
