package Controller.Incident

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R
import java.util.Calendar

class WaterServActivity : AppCompatActivity() {
    private lateinit var etReporter: EditText
    private lateinit var etLocation: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnDate: ImageButton
    private lateinit var spinnerSeverity: Spinner
    private lateinit var btnSave: Button
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_serv)

        etReporter = findViewById(R.id.etReporterWater)
        etLocation = findViewById(R.id.etLocationWater)
        etDescription = findViewById(R.id.etDescriptionWater)
        btnDate = findViewById(R.id.btnDateWater)
        spinnerSeverity = findViewById(R.id.spinnerSeverityWater)
        btnSave = findViewById(R.id.btnSaveWater)

        setupSpinner(spinnerSeverity)
        setupDatePicker(btnDate)
        btnSave.setOnClickListener { saveReport() }
    }

    private fun setupSpinner(spinner: Spinner) {
        val severities = listOf(
            getString(R.string.severity_low),
            getString(R.string.severity_medium),
            getString(R.string.severity_high)
        )
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, severities)
    }

    private fun setupDatePicker(button: ImageButton) {
        button.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "%04d-%02d-%02d".format(y, m + 1, d)
                Toast.makeText(this, selectedDate, Toast.LENGTH_SHORT).show()
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun saveReport() {
        val reporter = etReporter.text.toString().trim()
        val location = etLocation.text.toString().trim()
        val description = etDescription.text.toString().trim()
        val severity = spinnerSeverity.selectedItem?.toString() ?: ""
        val date = selectedDate

        if (location.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, getString(R.string.error_required_fields), Toast.LENGTH_SHORT).show()
            return
        }

        val prefs = getSharedPreferences("water_reports", MODE_PRIVATE)
        prefs.edit()
            .putString("reporter", reporter)
            .putString("location", location)
            .putString("description", description)
            .putString("severity", severity)
            .putString("date", date)
            .apply()
        Toast.makeText(this, getString(R.string.report_saved), Toast.LENGTH_SHORT).show()
        finish()
    }
}