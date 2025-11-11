package Data

import Entity.Visit

object VisitDataManager {
    private val visits = mutableListOf<Visit>()
    private var currentId = 1

    init {
        // Datos ya ingresados para usarlos de ejemplo
        visits.add(Visit(
            id = currentId++,
            residentName = "Carlos Rodríguez",
            visitorName = "María González",
            visitDate = "2025-11-10",
            visitTime = "14:00",
            purpose = "Visita familiar",
            status = "Programada"
        ))
        visits.add(Visit(
            id = currentId++,
            residentName = "Ana Martínez",
            visitorName = "Juan Pérez",
            visitDate = "2025-11-11",
            visitTime = "10:30",
            purpose = "Entrega de paquete",
            status = "Programada"
        ))
        visits.add(Visit(
            id = currentId++,
            residentName = "Luis Vargas",
            visitorName = "Sofia Castro",
            visitDate = "2025-11-09",
            visitTime = "16:00",
            purpose = "Reunión social",
            status = "Completada"
        ))
    }

    fun getAllVisits(): List<Visit> {
        return visits.toList()
    }

    fun getVisitById(id: Int): Visit? {
        return visits.find { it.id == id }
    }

    fun addVisit(visit: Visit) {
        visit.id = currentId++
        visits.add(visit)
    }

    fun updateVisit(visit: Visit): Boolean {
        val index = visits.indexOfFirst { it.id == visit.id }
        return if (index != -1) {
            visits[index] = visit
            true
        } else {
            false
        }
    }

    fun deleteVisit(id: Int): Boolean {
        return visits.removeIf { it.id == id }
    }

    fun getVisitsByStatus(status: String): List<Visit> {
        return visits.filter { it.status == status }
    }
}