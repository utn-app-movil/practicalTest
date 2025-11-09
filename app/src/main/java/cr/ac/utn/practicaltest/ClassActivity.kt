package cr.ac.utn.practicaltest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ClassActivity : AppCompatActivity() {


    private var isClassSelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_class)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.schedule)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnSelectClass = findViewById<Button>(R.id.btnSelect_class)
        btnSelectClass.setOnClickListener {
            if (!isClassSelected) {
                btnSelectClass.text = "Clase seleccionada"
                isClassSelected = true
            }
        }


        val btnSelectClass2 = findViewById<Button>(R.id.btnSelect_class2)
        btnSelectClass2.setOnClickListener {
            if (!isClassSelected) {
                btnSelectClass.text = "Clase seleccionada"
                isClassSelected = true
            }
        }


        val btnSelectClass3 = findViewById<Button>(R.id.btnSelect_class3)
        btnSelectClass3.setOnClickListener {
            if (!isClassSelected) {
                btnSelectClass.text = "Clase seleccionada"
                isClassSelected = true
            }
        }


        val btnBack = findViewById<Button>(R.id.btnBack_Class)
        btnBack.setOnClickListener {
            startActivity(Intent(this, LearningActivity::class.java))
        }
    }
}