package cr.ac.utn.practicaltest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import Controller.SurveyController
import Util.QuestionAdapter
import android.widget.Button
import kotlin.text.contains


class SurveyActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuestionAdapter
    private lateinit var controller: SurveyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        controller = SurveyController(this)

        recyclerView = findViewById(R.id.recyclerViewQuestions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = QuestionAdapter(emptyList()) { questionId, answer ->
            controller.saveAnswer(questionId, answer)
        }

        recyclerView.adapter = adapter


        adapter.updateQuestions(controller.getQuestions())

        val btnSubmit = findViewById<Button>(R.id.btnSubmitSurvey)
        btnSubmit.setOnClickListener {
            try {
                val result = controller.submitSurvey()

                Toast.makeText(this, result, Toast.LENGTH_LONG).show()
                if (result.contains("éxito")) {
                    adapter.updateQuestions(controller.getQuestions())
                }
            } catch (e: Exception) {

                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
