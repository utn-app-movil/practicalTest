package Entity

// Sports class (actividad deportiva)
data class LearningClass(
    val id: Int = 0,
    val title: String,      // nombre de la clase
    val schedule: String,   // horario
    val professor: String   // profesor
) {
    override fun toString(): String = "$title  •  $schedule  •  $professor"
}
