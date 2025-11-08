package cr.ac.utn.practicaltest

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_survey)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // === REFERENCIAS A VIEWS (CORREGIDAS) ===
        val etId = findViewById<EditText>(R.id.etId)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etFechaNacimiento = findViewById<EditText>(R.id.etFechaNacimiento)
        val spinnerEstadoCivil = findViewById<Spinner>(R.id.spinnerEstadoCivil)
        val etIngresos = findViewById<EditText>(R.id.etIngresos)
        val etProvincia = findViewById<EditText>(R.id.etProvincia)
        val etCanton = findViewById<EditText>(R.id.etCanton)
        val etDistrito = findViewById<EditText>(R.id.etDistrito)
        val etDireccion = findViewById<EditText>(R.id.etDireccion)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val btnCalendar = findViewById<ImageButton>(R.id.btnCalendar)
        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)

        // === SPINNER: Estado Civil ===
        ArrayAdapter.createFromResource(
            this,
            R.array.estado_civil,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerEstadoCivil.adapter = adapter
        }

        // === DATE PICKER ===
        val calendar = Calendar.getInstance()
        btnCalendar.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    etFechaNacimiento.setText(String.format("%02d/%02d/%d", day, month + 1, year))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // === BOTÓN ENVIAR ENCUESTA ===
        btnRegistrar.setOnClickListener {
            if (validarCampos(
                    etId, etNombre, etFechaNacimiento, spinnerEstadoCivil,
                    etIngresos, etProvincia, etCanton, etDistrito,
                    etDireccion, etTelefono
                )) {
                Toast.makeText(this, "¡Encuesta enviada con éxito!", Toast.LENGTH_LONG).show()
                limpiarFormulario()
            }
        }
    }

    private fun validarCampos(
        etId: EditText, etNombre: EditText, etFechaNacimiento: EditText,
        spinnerEstadoCivil: Spinner, etIngresos: EditText,
        etProvincia: EditText, etCanton: EditText, etDistrito: EditText,
        etDireccion: EditText, etTelefono: EditText
    ): Boolean {
        if (etId.text.toString().trim().length !in 9..10) {
            etId.error = "Cédula inválida (9-10 dígitos)"
            return false
        }
        if (etNombre.text.isNullOrEmpty()) { etNombre.error = "Requerido"; return false }
        if (etFechaNacimiento.text.isNullOrEmpty()) { etFechaNacimiento.error = "Requerido"; return false }
        if (spinnerEstadoCivil.selectedItemPosition == 0) {
            Toast.makeText(this, "Seleccione estado civil", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etIngresos.text.isNullOrEmpty()) { etIngresos.error = "Requerido"; return false }
        if (etProvincia.text.isNullOrEmpty()) { etProvincia.error = "Requerido"; return false }
        if (etCanton.text.isNullOrEmpty()) { etCanton.error = "Requerido"; return false }
        if (etDistrito.text.isNullOrEmpty()) { etDistrito.error = "Requerido"; return false }
        if (etDireccion.text.isNullOrEmpty()) { etDireccion.error = "Requerido"; return false }
        if (etTelefono.text.toString().length != 8) { etTelefono.error = "8 dígitos"; return false }
        return true
    }

    private fun limpiarFormulario() {
        findViewById<EditText>(R.id.etId).text.clear()
        findViewById<EditText>(R.id.etNombre).text.clear()
        findViewById<EditText>(R.id.etFechaNacimiento).text.clear()
        findViewById<Spinner>(R.id.spinnerEstadoCivil).setSelection(0)
        findViewById<EditText>(R.id.etIngresos).text.clear()
        findViewById<EditText>(R.id.etProvincia).text.clear()
        findViewById<EditText>(R.id.etCanton).text.clear()
        findViewById<EditText>(R.id.etDistrito).text.clear()
        findViewById<EditText>(R.id.etDireccion).text.clear()
        findViewById<EditText>(R.id.etTelefono).text.clear()
        findViewById<EditText>(R.id.etCorreo).text.clear()
    }
}