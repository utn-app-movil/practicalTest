package cr.ac.utn.practicaltest

import Controller.PersonController
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class TrainingActivity : AppCompatActivity() {

    private lateinit var TextId: EditText
    private lateinit var TextName: EditText
    private lateinit var TextFLastName: EditText
    private lateinit var TextEmail: EditText
    private lateinit var personController: PersonController
    private var isEditMode: Boolean = false
    private lateinit var btnSearchPerson: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_training)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}