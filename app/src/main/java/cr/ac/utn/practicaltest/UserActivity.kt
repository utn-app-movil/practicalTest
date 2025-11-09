package cr.ac.utn.practicaltest

import Entity.Person
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class UserActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var etId: EditText
    private lateinit var etName: EditText
    private lateinit var etFLastName: EditText
    private lateinit var etSLastName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var tvBirthday: TextView
    private lateinit var etState: EditText
    private lateinit var etDistrict: EditText
    private lateinit var etAddress: EditText

    private lateinit var btnSearchId: ImageButton
    private lateinit var btnCalendar: ImageButton

    private var day = 0
    private var month = 0
    private var year = 0
    private var isEditMode = false

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val photo = result.data?.extras?.get("data") as Bitmap
            // Aquí puedes guardar la foto si quieres, por ahora solo se muestra
            Toast.makeText(this, "Foto capturada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // VINCULAR VISTAS
        etId = findViewById(R.id.etId)
        etName = findViewById(R.id.etName)
        etFLastName = findViewById(R.id.etFLastName)
        etSLastName = findViewById(R.id.etSLastName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        tvBirthday = findViewById(R.id.tvBirthday)
        etState = findViewById(R.id.etState)
        etDistrict = findViewById(R.id.etDistrict)
        etAddress = findViewById(R.id.etAddress)
        btnSearchId = findViewById(R.id.btnSearchId)
        btnCalendar = findViewById(R.id.btnCalendar)

        // FLECHA DE VOLVER
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // BUSCAR AUTOMÁTICAMENTE AL ESCRIBIR 8 DÍGITOS
        etId.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length >= 8) searchPerson()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // LUPA MANUAL
        btnSearchId.setOnClickListener { searchPerson() }

        // CALENDARIO
        tvBirthday.setOnClickListener { showDatePickerDialog() }
        btnCalendar.setOnClickListener { showDatePickerDialog() }

        resetDate()
    }

    private fun getDateString(day: Int, month: Int, year: Int): String {
        return String.format("%02d/%02d/%04d", day, month + 1, year)
    }

    override fun onDateSet(view: android.widget.DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        tvBirthday.text = getDateString(dayOfMonth, month, year)
    }

    private fun resetDate() {
        val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(this, this, year, month, day).apply {
            datePicker.maxDate = System.currentTimeMillis()
            show()
        }
    }

    private fun searchPerson() {
        val id = etId.text.toString().trim()
        if (id.isEmpty()) return

        val sp = getSharedPreferences("PersonData", MODE_PRIVATE)
        val savedId = sp.getString("person_id", null)

        if (savedId == id) {
            isEditMode = true
            etId.isEnabled = false

            etName.setText(sp.getString("person_name", ""))
            etFLastName.setText(sp.getString("person_flastname", ""))
            etSLastName.setText(sp.getString("person_slastname", ""))
            etPhone.setText(sp.getString("person_phone", ""))
            etEmail.setText(sp.getString("person_email", ""))
            tvBirthday.text = sp.getString("person_birthday", "")
            etState.setText(sp.getString("person_state", ""))
            etDistrict.setText(sp.getString("person_district", ""))
            etAddress.setText(sp.getString("person_address", ""))

            Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show()
            invalidateOptionsMenu()
        }
    }

    private fun cleanScreen() {
        isEditMode = false
        etId.isEnabled = true
        etId.text.clear()
        etName.text.clear()
        etFLastName.text.clear()
        etSLastName.text.clear()
        etPhone.text.clear()
        etEmail.text.clear()
        tvBirthday.text = ""
        etState.text.clear()
        etDistrict.text.clear()
        etAddress.text.clear()
        resetDate()
        invalidateOptionsMenu()
    }

    private fun savePerson() {
        if (etId.text.isEmpty() || etName.text.isEmpty() || tvBirthday.text.isEmpty()) {
            Toast.makeText(this, "ID, Nombre y Fecha de nacimiento son obligatorios", Toast.LENGTH_LONG).show()
            return
        }

        val sp = getSharedPreferences("PersonData", MODE_PRIVATE).edit()
        sp.putString("person_id", etId.text.toString().trim())
        sp.putString("person_name", etName.text.toString().trim())
        sp.putString("person_flastname", etFLastName.text.toString().trim())
        sp.putString("person_slastname", etSLastName.text.toString().trim())
        sp.putString("person_phone", etPhone.text.toString().trim())
        sp.putString("person_email", etEmail.text.toString().trim())
        sp.putString("person_birthday", tvBirthday.text.toString())
        sp.putString("person_state", etState.text.toString().trim())
        sp.putString("person_district", etDistrict.text.toString().trim())
        sp.putString("person_address", etAddress.text.toString().trim())
        sp.apply()

        Toast.makeText(this, "¡Usuario guardado con éxito!", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun deletePerson() {
        getSharedPreferences("PersonData", MODE_PRIVATE).edit().clear().apply()
        cleanScreen()
        Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_SHORT).show()
    }

    // MENÚ CRUD
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        menu?.findItem(R.id.mnu_delete)?.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.mnu_save -> { savePerson(); true }
            R.id.mnu_delete -> { deletePerson(); true }
            R.id.mnu_cancel -> { cleanScreen(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}