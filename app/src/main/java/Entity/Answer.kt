package Entity

class Answer {
    private var questionId: Long = 0
    private var answerText: String = ""

    constructor()

    constructor(question: Long, answerText: String){
        this.questionId = question
        this.answerText = answerText

    }

    var QuestionId: Long
        get() = this.questionId
        set(value) {this.questionId = value}

    var AnswerText: String
        get() = this.answerText
        set(value) {this.answerText = value}


}