package cr.ac.utn.practicaltest

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ParticipantActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var etFirstLastname: EditText
    private lateinit var etSecondLastname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var tvBirthday: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_participant)


        etId = findViewById(R.id.et_participant_id)
        etName = findViewById(R.id.et_participant_name)
        etFirstLastname = findViewById(R.id.et_participant_flastname)
        etSecondLastname = findViewById(R.id.et_participant_slastname)
        etEmail = findViewById(R.id.et_participant_email)
        etPhone = findViewById(R.id.et_participant_phone)
        tvBirthday = findViewById(R.id.et_participant_birthday)


        tvBirthday.setOnClickListener {
            showDatePicker()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                saveParticipant()
                true
            }
            R.id.mnu_delete -> {
                deleteParticipant()
                true
            }
            R.id.mnu_cancel -> {
                clearFields()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, y, m, d ->
            val selectedDate = "$d/${m + 1}/$y"
            tvBirthday.text = selectedDate
        }, year, month, day)

        datePicker.show()
    }


    private fun saveParticipant() {
        val id = etId.text.toString().trim()
        val name = etName.text.toString().trim()
        val flastname = etFirstLastname.text.toString().trim()
        val slastname = etSecondLastname.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        val birthday = tvBirthday.text.toString().trim()

        if (id.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Complete los campos obligatorios (Cédula y Nombre)", Toast.LENGTH_SHORT).show()
            return
        }



        Toast.makeText(this, "Participante guardado exitosamente", Toast.LENGTH_SHORT).show()
    }


    private fun deleteParticipant() {
        val id = etId.text.toString().trim()

        if (id.isEmpty()) {
            Toast.makeText(this, "Ingrese el ID para eliminar", Toast.LENGTH_SHORT).show()
            return
        }


        Toast.makeText(this, "Participante eliminado", Toast.LENGTH_SHORT).show()
    }

    private fun clearFields() {
        etId.text?.clear()
        etName.text?.clear()
        etFirstLastname.text?.clear()
        etSecondLastname.text?.clear()
        etEmail.text?.clear()
        etPhone.text?.clear()
        tvBirthday.text = ""
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show()
    }
}