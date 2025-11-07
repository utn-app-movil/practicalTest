package Entity

class Training {
    private var TrainingId:         String=""
    private var name:       String=""
    private var LastName:   String=""


    constructor(TrainingId: String, name: String, LastName: String)
    {
        this.TrainingId  = TrainingId
        this.name       = name
        this.LastName       = LastName
    }

    var idTraining: String
        get() = this.idTraining
        set(value) {this.idTraining=value}

    var nameTraining: String
        get() = this.nameTraining
        set(value) {this.nameTraining=value}

    var LastNameTraining: String
        get() = this.LastNameTraining
        set(value) {this.LastNameTraining=value}


    fun FullName() = "$this.name $this.LastName"

}