package Entity

class Question {

    private var id: Long = 0
    private var questionText: String = ""
    private var type: String = ""

    constructor()

    constructor(id: Long, questionText: String, type: String){
        this.id = id
        this.questionText = questionText
        this.type = type
    }

    var ID: Long
        get() = this.id
        set(value) { this.id = value}

    var QuestionText: String
        get() = this.questionText
        set(value) { this.questionText = value}

    var Type:String
        get() = this.type
        set(value) {this.type = value}

}