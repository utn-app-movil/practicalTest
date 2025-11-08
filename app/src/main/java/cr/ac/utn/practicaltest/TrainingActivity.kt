package cr.ac.utn.practicaltest

import Controller.InscriptionController
import Entity.Inscription
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class TrainingActivity : AppCompatActivity() {

    private lateinit var TextId: EditText
    private lateinit var TextName: EditText
    private lateinit var TextEmail: EditText
    private lateinit var TextCourse: EditText
    private lateinit var TextSchedule: EditText
    private lateinit var TextTraining: EditText

    private lateinit var btnSearchInscription: ImageButton
    private lateinit var inscriptionController: InscriptionController
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_training)

        inscriptionController = InscriptionController(this)

        TextId = findViewById(R.id.TextID)
        TextName = findViewById(R.id.TextName)
        TextEmail = findViewById(R.id.TextEmail)
        TextCourse = findViewById(R.id.TextCourse)
        TextSchedule = findViewById(R.id.TextSchedule)
        TextTraining = findViewById(R.id.TextTraining)

        btnSearchInscription = findViewById(R.id.btnSearchId_person)
        btnSearchInscription.setOnClickListener { searchInscription() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                saveInscription()
                true
            }
            R.id.mnu_delete -> {
                deleteInscription()
                true
            }
            R.id.mnu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun clear() {
        TextId.text.clear()
        TextName.text.clear()
        TextEmail.text.clear()
        TextCourse.text.clear()
        TextSchedule.text.clear()
        TextTraining.text.clear()
        isEditMode = false
    }

    fun isValidate(): Boolean =
        TextId.text.isNotBlank() &&
                TextEmail.text.isNotBlank() &&
                TextCourse.text.isNotBlank() &&
                TextSchedule.text.isNotBlank() &&
                TextTraining.text.isNotBlank()

    fun searchInscription() {
        try {
            val id = TextId.text.toString().trim()
            if (id.isBlank()) {
                Toast.makeText(this, R.string.ErrorMsgGetById, Toast.LENGTH_LONG).show()
                return
            }
            val inscription = inscriptionController.getByIdInscription(id)
            if (inscription == null) {
                Toast.makeText(this, R.string.ErrorMsgGetById, Toast.LENGTH_LONG).show()
                clear()
            } else {
                TextName.setText(inscription.idPerson)
                TextEmail.setText(inscription.PersonEmail)
                TextCourse.setText(inscription.CourseName)
                TextSchedule.setText(inscription.Schedule)
                TextTraining.setText(inscription.TrainingName)
                isEditMode = true
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: getString(R.string.ErrorMsgGetById), Toast.LENGTH_LONG).show()
        }
    }

    fun saveInscription() {
        try {
            if (isValidate()) {
                val id = TextId.text.toString().trim()
                val existing = inscriptionController.getByIdInscription(id)
                val ins = Inscription()
                ins.InscriptionId = id
                ins.PersonId = TextName.text.toString()
                ins.PersonEmail = TextEmail.text.toString()
                ins.CourseName = TextCourse.text.toString()
                ins.TrainingName = TextTraining.text.toString()
                ins.Schedule = TextSchedule.text.toString()
                if (existing != null && !isEditMode) {
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_LONG).show()
                } else {
                    if (isEditMode) {
                        inscriptionController.updateInscription(ins)
                        Toast.makeText(this, getString(R.string.MsgUpdateSuccess), Toast.LENGTH_LONG).show()
                    } else {
                        inscriptionController.addInscription(ins)
                        Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_LONG).show()
                    }
                    clear()
                }
            } else {
                Toast.makeText(this, R.string.ErrorMsgAdd, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    fun deleteInscription() {
        try {
            val id = TextId.text.toString().trim()
            if (id.isBlank()) {
                Toast.makeText(this, R.string.ErrorMsgGetById, Toast.LENGTH_LONG).show()
            } else {
                val existing = inscriptionController.getByIdInscription(id)
                if (existing == null) {
                    Toast.makeText(this, R.string.ErrorMsgRemove, Toast.LENGTH_LONG).show()
                } else {
                    inscriptionController.removeInscription(id)
                    Toast.makeText(this, R.string.MsgDeleteSuccess, Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
        clear()
    }
}
