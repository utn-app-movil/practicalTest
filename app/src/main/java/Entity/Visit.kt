package Entity

data class Visit(
    var id: Int = 0,
    var residentName: String = "",
    var visitorName: String = "",
    var visitDate: String = "",
    var visitTime: String = "",
    var purpose: String = "",
    var status: String = "Programada" // Programada, Completada, Cancelada
)