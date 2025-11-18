package cr.ac.utn.practicaltest

import Controller.InventoryController
import Controller.PersonController
import Entity.Inventory
import Entity.Person
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate

class InventoryActivity : AppCompatActivity() {

    private lateinit var txtId: EditText
    private lateinit var txtResourceName: EditText
    private lateinit var txtCategory: EditText
    private lateinit var txtProvider: EditText
    private lateinit var txtQuantity: EditText
    private var IsEditMode: Boolean = false
    private lateinit var inventoryController: InventoryController
    private lateinit var menuItemDelete: MenuItem


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inventory)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->

            inventoryController = InventoryController(this)
            txtId = findViewById<EditText>(R.id.txtId)
            txtResourceName = findViewById<EditText>(R.id.txtResourceName)
            txtCategory = findViewById<EditText>(R.id.txtCategory)
            txtProvider = findViewById<EditText>(R.id.txtProvider)
            txtQuantity = findViewById<EditText>(R.id.txtQuantity)

            val btnSave = findViewById<Button>(R.id.btnSaveInventory)
            btnSave.setOnClickListener { view -> saveInventory() }

            val btnSearch = findViewById<ImageButton>(R.id.btnSearchInventory)
            btnSearch.setOnClickListener { view -> searchInventory(txtId.text.trim().toString()) }


            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun searchInventory(id: String){
        try {
            val inventory = inventoryController.getById(txtId.text.trim().toString())
            if (inventory != null){
                IsEditMode = true
                txtId.isEnabled = false
                txtResourceName.setText(inventory.ResourceName)
                txtCategory.setText(inventory.Category)
                txtProvider.setText(inventory.Provider)
                txtQuantity.setText(inventory.Quantity.toString())
            }else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanScreen(){
        IsEditMode = false
        txtId.isEnabled = true
        txtId.setText("")
        IsEditMode = true
        txtResourceName.setText("")
        txtCategory.setText("")
        txtProvider.setText("")
        txtQuantity.setText("").toString()
        invalidateMenu()
    }

    fun isValidationData(): Boolean{
        return txtId.text.trim().isNotEmpty() && txtResourceName.text.trim().isNotEmpty()
                && (txtCategory.text.trim().isNotEmpty() && txtProvider.text.trim().isNotEmpty()
                && txtQuantity.text.toString()?.toInt()!! != null && txtQuantity.text.toString()?.toInt()!! != 0)
    }

    fun saveInventory() {
        try {
            if (isValidationData()) {
                if (inventoryController.getById(txtId.text.toString().trim()) != null
                    && !IsEditMode
                ) {
                    Toast.makeText(this, getString(R.string.MsgDuplicateData)
                        , Toast.LENGTH_LONG).show()
                }else{
                    val inventory = Inventory()
                    inventory.ID = txtId.text.toString()
                    inventory.ResourceName = txtResourceName.text.toString()
                    inventory.Category = txtCategory.text.toString()
                    inventory.Provider = txtProvider.text.toString()
                    inventory.Quantity = txtQuantity.text.toString().toInt()

                    if (!IsEditMode)
                        inventoryController.addInventory(inventory)
                    else
                        inventoryController.updateInventory(inventory)

                    cleanScreen()

                    Toast.makeText(this, getString(R.string.MsgSaveSuccess)
                        , Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Datos incompletos"
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    fun deletePerson() {
        try {
            inventoryController.removeInventory(txtId.text.trim().toString())
            cleanScreen()
            Toast.makeText(this,
                getString(
                    R.string.MsgDeleteSuccess),
                Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = IsEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_delete ->{
                Util.Util.showDialogCondition(this
                    , getString(R.string.TextDelete_menu)
                    , { deletePerson() })
                return true
            }
            R.id.mnu_cancel ->{
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}