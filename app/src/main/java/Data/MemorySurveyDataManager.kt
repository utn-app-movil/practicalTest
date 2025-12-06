package Data

import Entity.Answer
import Entity.Question

object MemorySurveyDataManager: ISurveyDataManager {

        private val questions = listOf(
            Question(1,"¿Cómo calificarías el servicio?", "rating"),
            Question(2,"¿Cómo calificarías la atención?", "rating"),
            Question(3,"¿Tienes sugerencias para mejorar el servicio?", "text"),

        )

        private val answers = mutableListOf<Answer>()

    override fun getQuestions(): List<Question> = questions


    override fun saveAnswer(nanswer: Answer) {
        answers.removeAll {it.QuestionId == nanswer.QuestionId}
        answers.add(nanswer)
    }

    override fun getAnswers(): List<Answer> = answers


    override fun clearAnswers() {
        answers.clear()
    }
}