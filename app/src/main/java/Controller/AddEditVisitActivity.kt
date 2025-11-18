package Controller

import Data.VisitDataManager
import Entity.Visit
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R

class AddEditVisitActivity : AppCompatActivity() {

    private lateinit var etResidentName: EditText
    private lateinit var etVisitorName: EditText
    private lateinit var etVisitDate: EditText
    private lateinit var etVisitTime: EditText
    private lateinit var etPurpose: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var visitId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_visit)

        // Inicializar vistas
        etResidentName = findViewById(R.id.etResidentName)
        etVisitorName = findViewById(R.id.etVisitorName)
        etVisitDate = findViewById(R.id.etVisitDate)
        etVisitTime = findViewById(R.id.etVisitTime)
        etPurpose = findViewById(R.id.etPurpose)
        spinnerStatus = findViewById(R.id.spinnerStatus)
        btnSave = findViewById(R.id.btnSaveVisit)
        btnCancel = findViewById(R.id.btnCancelVisit)

        // Configurar Spinner de estado
        setupStatusSpinner()

        // Verificar si es modo edición
        visitId = intent.getIntExtra("VISIT_ID", -1)
        if (visitId != -1) {
            isEditMode = true
            loadVisitData()
        }

        // Botón guardar
        btnSave.setOnClickListener {
            saveVisit()
        }

        // Botón cancelar
        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setupStatusSpinner() {
        val statuses = arrayOf("Programada", "Completada", "Cancelada")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter
    }

    private fun loadVisitData() {
        val visit = VisitDataManager.getVisitById(visitId)
        visit?.let {
            etResidentName.setText(it.residentName)
            etVisitorName.setText(it.visitorName)
            etVisitDate.setText(it.visitDate)
            etVisitTime.setText(it.visitTime)
            etPurpose.setText(it.purpose)

            // Seleccionar estado en spinner
            val statusPosition = when (it.status) {
                "Programada" -> 0
                "Completada" -> 1
                "Cancelada" -> 2
                else -> 0
            }
            spinnerStatus.setSelection(statusPosition)
        }
    }

    private fun saveVisit() {
        // Validar campos
        val residentName = etResidentName.text.toString().trim()
        val visitorName = etVisitorName.text.toString().trim()
        val visitDate = etVisitDate.text.toString().trim()
        val visitTime = etVisitTime.text.toString().trim()
        val purpose = etPurpose.text.toString().trim()
        val status = spinnerStatus.selectedItem.toString()

        if (residentName.isEmpty() || visitorName.isEmpty() ||
            visitDate.isEmpty() || visitTime.isEmpty() || purpose.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val visit = Visit(
            id = if (isEditMode) visitId else 0,
            residentName = residentName,
            visitorName = visitorName,
            visitDate = visitDate,
            visitTime = visitTime,
            purpose = purpose,
            status = status
        )

        if (isEditMode) {
            if (VisitDataManager.updateVisit(visit)) {
                Toast.makeText(this, "Visita actualizada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
            }
        } else {
            VisitDataManager.addVisit(visit)
            Toast.makeText(this, "Visita agregada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}