package cr.ac.utn.practicaltest

import Controller.VehicleController
import Entity.Vehicle
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText

import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Data.MemoryDataManager
import Data.MemoryVehicleManager

class VehicleActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtPlate: EditText
    private lateinit var txtOwner: EditText
    private lateinit var txtBrand: EditText
    private lateinit var txtModel: EditText
    private lateinit var txtColor: EditText
    private lateinit var lstVehicles: ListView

    private lateinit var vehicleController: VehicleController
    private var isEditMode: Boolean = false
    private lateinit var menuItemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vehicle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización del controlador
        vehicleController = VehicleController(this)

        // Campos de texto
        txtId = findViewById(R.id.txtId_Agregar)
        txtPlate = findViewById(R.id.txtPlate_Agregar)
        txtOwner = findViewById(R.id.txtOwner_Agregar)
        txtBrand = findViewById(R.id.txtBrand_Agregar)
        txtModel = findViewById(R.id.txtModel_Agregar)
        txtColor = findViewById(R.id.txtColor_Agregar)
        lstVehicles = findViewById(R.id.lstVehicles)

        // Botón de eliminar
        val btnDelete = findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener { deleteVehicle() }

        // Botón guardar
        val btnSave = findViewById<Button>(R.id.btnSave_Agregar)
        btnSave.setOnClickListener { saveVehicle() }

        // Botón limpiar
        val btnClear = findViewById<Button>(R.id.btnClear_Agregar)
        btnClear.setOnClickListener { clearScreen() }

        // Mostrar lista al iniciar
        updateVehicleList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                saveVehicle()
                true
            }
            R.id.mnu_delete -> {
                deleteVehicle()
                true
            }
            R.id.mnu_cancel -> {
                clearScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun validateData(): Boolean {
        return txtId.text.isNotEmpty() &&
                txtPlate.text.isNotEmpty() &&
                txtOwner.text.isNotEmpty() &&
                txtBrand.text.isNotEmpty()
    }

    private fun saveVehicle() {
        try {
            if (validateData()) {
                val vehicle = Vehicle()
                vehicle.ID = txtId.text.toString()
                vehicle.PlateNumber = txtPlate.text.toString()
                vehicle.OwnerName = txtOwner.text.toString()
                vehicle.Brand = txtBrand.text.toString()
                vehicle.Model = txtModel.text.toString()
                vehicle.Color = txtColor.text.toString()

                if (vehicleController.getById(vehicle.ID) != null && !isEditMode) {
                    Toast.makeText(this, "Vehicle already exists.", Toast.LENGTH_LONG).show()
                } else {
                    if (!isEditMode)
                        vehicleController.addVehicle(vehicle)
                    else
                        vehicleController.updateVehicle(vehicle)

                    clearScreen()
                    Toast.makeText(this, "Vehicle saved successfully.", Toast.LENGTH_LONG).show()
                    updateVehicleList()
                }
            } else {
                Toast.makeText(this, "Please fill all required fields.", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteVehicle() {
        try {
            val id = txtId.text.toString()
            if (id.isEmpty()) {
                Toast.makeText(this, "Enter an ID to delete", Toast.LENGTH_SHORT).show()
                return
            }

            vehicleController.removeVehicle(id)
            clearScreen()
            Toast.makeText(this, "Vehicle deleted successfully.", Toast.LENGTH_LONG).show()
            updateVehicleList()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun clearScreen() {
        isEditMode = false
        txtId.isEnabled = true
        txtId.text.clear()
        txtPlate.text.clear()
        txtOwner.text.clear()
        txtBrand.text.clear()
        txtModel.text.clear()
        txtColor.text.clear()
        invalidateOptionsMenu()
    }

    private fun updateVehicleList() {
        try {
            val list = MemoryVehicleManager.getAll()
            val vehicleList = list.map { "${it.PlateNumber} - ${it.OwnerName} - ${it.Brand} ${it.Model}" }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, vehicleList)
            lstVehicles.adapter = adapter
        } catch (e: Exception) {
            Toast.makeText(this, "Error loading vehicles", Toast.LENGTH_SHORT).show()
        }
    }
}
