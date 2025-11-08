package cr.ac.utn.practicaltest.harvest

data class HarvestEntity(
    val id: Int,
    val name: String,
    val responsible: String,
    val sowDate: String,
    val harvestDate: String
)