package Controller

import Data.ISurveyDataManager
import Data.MemorySurveyDataManager
import Entity.Answer
import android.content.Context
import cr.ac.utn.practicaltest.R
import kotlin.collections.joinToString


class SurveyController (private val context: Context) {
    private val dataManager: ISurveyDataManager = MemorySurveyDataManager

    fun getQuestions() = dataManager.getQuestions()

    fun saveAnswer(questionId:Long, answerText: String) {
        try {
            val answer = Answer(questionId,answerText)
            dataManager.saveAnswer(answer)
        }catch (e: Exception) {
            throw kotlin.Exception(context.getString(R.string.ErrorMsgSaveAnswer))
        }
    }

    fun submitSurvey(): String {
        try {
            val answer = dataManager.getAnswers()
            if(answer.size < dataManager.getQuestions().size){
                return  context.getString(R.string.MsgIncompleteSurvey)
            }
            val summary = answer.joinToString("\n") {"Pregunta ${it.QuestionId}: ${it.AnswerText}"}
            dataManager.clearAnswers()
            return context.getString(R.string.MsgSurveySubmitted) + "\n$summary"
        }catch (e: Exception) {

            throw kotlin.Exception(context.getString(R.string.ErrorMsgSubmitSurvey))
        }
    }
}