package Entity

data class Training(
    var id_training: String,
    var name: String,
    var description: String,
    var schedule: String,
    var capacity: Int,
    var instructor: String
)