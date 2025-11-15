package cr.ac.utn.practicaltest

import Controller.VehicleController
import Entity.Vehicle
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class VehicleActivity : AppCompatActivity() {

    private lateinit var TextID: TextInputEditText
    private lateinit var TextName: TextInputEditText
    private lateinit var TextModel: TextInputEditText
    private lateinit var TextBrand: TextInputEditText
    private lateinit var TextPlate: TextInputEditText
    private lateinit var TextYear: TextInputEditText
    private lateinit var TextColor: TextInputEditText
    private var IsEditMode: Boolean = false

    private lateinit var vehicleController: VehicleController
    private lateinit var menuitemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_vehicle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        vehicleController = VehicleController(this)

        TextID        = findViewById(R.id.TextID_vehicle)
        TextName      = findViewById(R.id.TextOwnerName_vehicle)
        TextModel = findViewById(R.id.TextModel_vehicle)
        TextBrand = findViewById(R.id.TextBrand_vehicle)
        TextPlate     = findViewById(R.id.TextPlate_vehicle)
        TextYear     = findViewById(R.id.TextYear_vehicle)
        TextColor = findViewById(R.id.TextColor_vehicle)



        val btnSearch = findViewById<ImageButton?>(R.id.btnSearch_vehicle)
        btnSearch?.setOnClickListener {
            searchVehicle(TextID.text?.trim().toString())
        }


        val btnreturn = findViewById<ImageButton>(R.id.btnVolver)
        btnreturn.setOnClickListener(View.OnClickListener{ view ->
            Util.Util.openActivity(this, MainActivity::class.java)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuitemDelete = menu.findItem(R.id.menu_Delete)
        menuitemDelete.isVisible = IsEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_Save -> {
                if (IsEditMode) {
                    Util.Util.showDialogCondition(
                        this, getString(R.string.TextSaveActionQuestion)
                    ) { savePerson() }
                } else {
                    savePerson()
                }
                true
            }
            R.id.menu_Delete -> {
                Util.Util.showDialogCondition(
                    this, getString(R.string.TextDeleteActionQuestion)
                ) { removeVehicle() }
                true
            }
            R.id.menu_Cancel -> {
                cleanScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchVehicle(id: String) {
        try {
            val profile = vehicleController.getById(id)
            if (profile != null) {
                IsEditMode = true
                TextID.isEnabled = false
                TextName.setText(profile.OwnerName)
                TextModel.setText(profile.Model)
                TextBrand.setText(profile.Brand)
                TextPlate.setText(profile.Plate.toString())
                TextYear.setText(profile.Year.toString())
                TextColor.setText(profile.Color)


                if (::menuitemDelete.isInitialized) menuitemDelete.isVisible = true
                invalidateOptionsMenu()
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.ErrorMsgGetById), Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanScreen() {
        IsEditMode = false
        TextID.isEnabled = true
        TextID.setText("")
        TextName.setText("")
        TextBrand.setText("")
        TextModel.setText("")
        TextYear.setText("")
        TextPlate.setText("")
        TextColor.setText("")
        if (::menuitemDelete.isInitialized) menuitemDelete.isVisible = false
        invalidateOptionsMenu()
    }

    private fun isValidationData(): Boolean {
        return TextID.text?.trim()?.isNotEmpty() == true
                && TextName.text?.trim()?.isNotEmpty() == true
                && TextYear.text?.trim()?.isNotEmpty() == true
                && TextColor.text?.trim()?.isNotEmpty() == true
                && TextBrand.text?.trim()?.isNotEmpty() == true
                && TextModel.text?.trim()?.isNotEmpty() == true
                && TextPlate.text?.trim()?.isNotEmpty() == true
    }

    private fun savePerson() {
        try {
            if (isValidationData()) {
                val exists = vehicleController.getById(TextID.text.toString()) != null
                if (exists && !IsEditMode) {
                    Toast.makeText(this, R.string.MsgDuplicateData, Toast.LENGTH_LONG).show()
                    return
                }

                val vehicle = Vehicle().apply {
                    ID = TextID.text.toString()
                    OwnerName = TextName.text.toString()
                    Model = TextModel.text.toString()
                    Brand = TextBrand.text.toString()
                    Plate = TextPlate.text.toString()
                    Year = TextYear.text.toString().toInt()
                    Color = TextColor.text.toString()
                }

                if (!IsEditMode)
                    vehicleController.addVehicle(vehicle)
                else
                    vehicleController.updateVehicle(vehicle)

                cleanScreen()
                Toast.makeText(this, R.string.MsgSaveSuccess, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, R.string.MsgMissingData, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: "Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun removeVehicle() {
        try {
            vehicleController.removeVehicle(TextID.text?.trim().toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: "Error", Toast.LENGTH_LONG).show()
        }
    }
}

