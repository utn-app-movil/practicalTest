package cr.ac.utn.practicaltest

import Util.Util
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LearningActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learning)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.learning)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        val btnScheduleLearning = findViewById<Button>(R.id.btnSchedule_Learning)
        btnScheduleLearning.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))

        }
        val btnClassLearning = findViewById<Button>(R.id.btnClasses_Learning)
        btnClassLearning.setOnClickListener {
            startActivity(Intent(this, ClassActivity::class.java))

        }
        val btnProfessorLearning = findViewById<Button>(R.id.btnProfessors_Learning)
        btnProfessorLearning.setOnClickListener {
            startActivity(Intent(this, ProfessorActivity::class.java))

        }

    }
}