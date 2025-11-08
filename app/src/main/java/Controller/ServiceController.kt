package Controller

import Entity.Service
import android.content.Context

class ServiceController(private val context: Context) {


    private val services = mutableListOf(
        Service(1, "Internet", "Servicio de conexión a internet de alta velocidad", 15000.0),
        Service(2, "Limpieza", "Servicio de limpieza semanal", 10000.0),
        Service(3, "Electricidad", "Pago mensual de energía eléctrica", 25000.0),
        Service(4, "Agua", "Consumo mensual de agua potable", 8000.0),
        Service(5, "Cable TV", "Televisión por cable con canales HD", 12000.0)
    )


    fun getAllServices(): List<Service> {
        return services
    }


    fun addService(service: Service) {
        services.add(service)
    }


    fun findServiceIdByName(name: String): Int {
        return services.find { it.name.equals(name, ignoreCase = true) }?.id ?: -1
    }


    fun updateService(updatedService: Service) {
        val index = services.indexOfFirst { it.id == updatedService.id }
        if (index != -1) {
            services[index] = updatedService
        }
    }


    fun deleteServiceByName(name: String) {
        val iterator = services.iterator()
        while (iterator.hasNext()) {
            val service = iterator.next()
            if (service.name.equals(name, ignoreCase = true)) {
                iterator.remove()
                break
            }
        }
    }


    fun removeService(id: Int) {
        services.removeAll { it.id == id }
    }
}
