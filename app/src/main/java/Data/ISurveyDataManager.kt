package Data

import Entity.Answer
import Entity.Question

interface ISurveyDataManager {
    fun getQuestions(): List<Question>
    fun saveAnswer(answer: Answer)
    fun getAnswers(): List<Answer>
    fun clearAnswers()
}


