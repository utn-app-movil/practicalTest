package cr.ac.utn.practicaltest

import Controller.SpaceController
import Entity.Space
import Entity.SpaceType
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID

class SpaceFormActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var etSpaceName: TextInputEditText
    private lateinit var spinnerSpaceType: Spinner
    private lateinit var etCapacity: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etPricePerHour: TextInputEditText
    private lateinit var cbAvailable: CheckBox
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var spaceController: SpaceController
    private var spaceId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_form)

        // Initialize controller
        spaceController = SpaceController(this)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        etSpaceName = findViewById(R.id.etSpaceName)
        spinnerSpaceType = findViewById(R.id.spinnerSpaceType)
        etCapacity = findViewById(R.id.etCapacity)
        etDescription = findViewById(R.id.etDescription)
        etPricePerHour = findViewById(R.id.etPricePerHour)
        cbAvailable = findViewById(R.id.cbAvailable)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Setup spinner
        setupSpinner()

        // Check if editing
        spaceId = intent.getStringExtra("SPACE_ID")
        if (spaceId != null) {
            isEditMode = true
            tvTitle.text = "Editar Espacio"
            loadSpace(spaceId!!)
        } else {
            tvTitle.text = "Agregar Espacio"
        }

        // Setup buttons
        btnSave.setOnClickListener {
            saveSpace()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setupSpinner() {
        val spaceTypes = SpaceType.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spaceTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSpaceType.adapter = adapter
    }

    private fun loadSpace(id: String) {
        val space = spaceController.getSpaceById(id)
        if (space != null) {
            etSpaceName.setText(space.Name)
            spinnerSpaceType.setSelection(space.Type.ordinal)
            etCapacity.setText(space.Capacity.toString())
            etDescription.setText(space.Description)
            etPricePerHour.setText(space.PricePerHour.toString())
            cbAvailable.isChecked = space.Available
        }
    }

    private fun saveSpace() {
        // Validate
        if (etSpaceName.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor ingrese el nombre del espacio", Toast.LENGTH_SHORT).show()
            return
        }

        if (etCapacity.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor ingrese la capacidad", Toast.LENGTH_SHORT).show()
            return
        }

        if (etPricePerHour.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor ingrese el precio por hora", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val space = Space(
                id = spaceId ?: UUID.randomUUID().toString(),
                name = etSpaceName.text.toString(),
                type = SpaceType.values()[spinnerSpaceType.selectedItemPosition],
                capacity = etCapacity.text.toString().toInt(),
                description = etDescription.text.toString(),
                available = cbAvailable.isChecked,
                pricePerHour = etPricePerHour.text.toString().toDouble()
            )

            val success = if (isEditMode) {
                spaceController.updateSpace(space)
            } else {
                spaceController.addSpace(space)
            }

            if (success) {
                Toast.makeText(
                    this,
                    if (isEditMode) "Espacio actualizado exitosamente" else "Espacio agregado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar el espacio", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
