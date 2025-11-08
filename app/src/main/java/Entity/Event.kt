
package cr.ac.utn.practicaltest.model

data class Event(
    var id: Int = 0,
    var title: String = "",
    var description: String = "",
    var date: String = "", // formato: "2025-04-05"
    var location: String = ""
)