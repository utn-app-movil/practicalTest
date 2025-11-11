package cr.ac.utn.practicaltest

import Controller.SupplierController
import Entity.Supplier
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton

class SupplierActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtPhone: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtContact_name: EditText
    private lateinit var txtAddress: EditText
    private lateinit var txtCountry: EditText
    private lateinit var txtCity: EditText
    private lateinit var txtPost_code: EditText

    private var isEditMode: Boolean = false
    private lateinit var supplierController: SupplierController
    private lateinit var menuItemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supplier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supplierController = SupplierController(this)


        txtId= findViewById<EditText>(R.id.idText)
        txtName=findViewById<EditText>(R.id.nameText)
        txtPhone=findViewById<EditText>(R.id.phoneText)
        txtEmail=findViewById<EditText>(R.id.emailText)
        txtContact_name=findViewById<EditText>(R.id.contnamText)
        txtAddress=findViewById<EditText>(R.id.addText)
        txtCountry=findViewById<EditText>(R.id.countryText)
        txtCity=findViewById<EditText>(R.id.cityText)
        txtPost_code=findViewById<EditText>(R.id.postcText)


        val btnSearchId = findViewById<ImageButton>(R.id.idBtn_supplier)
        btnSearchId.setOnClickListener(View.OnClickListener{ view ->
            searchSupplierId(txtId.text.trim().toString())
        })
        val btnSearchName = findViewById<ImageButton>(R.id.nameBtn_supplier)
        btnSearchName.setOnClickListener(View.OnClickListener{view ->
            searchSupplierName(txtName.text.trim().toString())
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save ->{
                if (isEditMode){
                    Util.Util.showDialogCondition(this
                        , getString(R.string.TextSaveActionQuestion)
                        , { saveSupplier() })
                }else{
                    saveSupplier()
                }
                return true
            }
            R.id.mnu_delete ->{
                Util.Util.showDialogCondition(this
                    , getString(R.string.TextDeleteActionQuestion)
                    , { deleteSupplier() })
                return true
            }
            R.id.mnu_cancel ->{
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun searchSupplierId(id: String){
        try {
            val supplier = supplierController.getById(id)
            if (supplier != null){
                isEditMode=true
                txtId.setText(supplier.ID.toString())
                txtId.isEnabled=false
                txtName.setText(supplier.Name)
                txtEmail.setText(supplier.Email)
                txtPhone.setText(supplier.Phone.toString())
                txtContact_name.setText(supplier.Contact_name)
                txtAddress.setText(supplier.Address)
                txtCountry.setText(supplier.Country)
                txtCity.setText(supplier.City)
                txtPost_code.setText(supplier.Post_code)
                menuItemDelete.isVisible = true
            }else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            cleanScreen()
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun searchSupplierName(name: String){
        try {
            val supplier = supplierController.getByName(name)
            if (supplier != null){
                isEditMode=true
                txtId.setText(supplier.ID.toString())
                txtId.isEnabled=false
                txtName.setText(supplier.Name)
                txtEmail.setText(supplier.Email)
                txtPhone.setText(supplier.Phone.toString())
                txtContact_name.setText(supplier.Contact_name)
                txtAddress.setText(supplier.Address)
                txtCountry.setText(supplier.Country)
                txtCity.setText(supplier.City)
                txtPost_code.setText(supplier.Post_code)
                menuItemDelete.isVisible = true
            }else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            cleanScreen()
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    fun isValidationData(): Boolean{

        return txtId.text.trim().isNotEmpty() && txtName.text.trim().isNotEmpty()
                && txtEmail.text.trim().isNotEmpty() && txtAddress.text.trim().isNotEmpty()
                && txtCountry.text.trim().isNotEmpty() && txtCity.text.trim().isNotEmpty()
                && txtPost_code.text.trim().isNotEmpty() && txtContact_name.text.trim().isNotEmpty()
                && (txtPhone.text.trim().isNotEmpty() && txtPhone.text.trim().length >= 8
                && txtPhone.text.toString()?.toInt()!! != null && txtPhone.text.toString()?.toInt()!! != 0)
    }

    private fun cleanScreen(){
        isEditMode=false
        txtId.isEnabled = true
        txtId.setText("")
        txtName.setText("")
        txtAddress.setText("")
        txtContact_name.setText("")
        txtEmail.setText("")
        txtPhone.setText("")
        txtCountry.setText("")
        txtCity.setText("")
        txtPost_code.setText("")
        invalidateOptionsMenu()
    }

    fun saveSupplier(){
        try {
            if (isValidationData()){
                if (supplierController.getById(txtId.text.toString().trim()) != null
                    && !isEditMode){
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate)
                        , Toast.LENGTH_LONG).show()
                }else{
                    val supplier = Supplier()
                    supplier.ID = txtId.text.toString()
                    supplier.Name = txtName.text.toString()
                    supplier.Email = txtEmail.text.toString()
                    supplier.Phone = txtPhone.text.toString().toInt()
                    supplier.Address = txtAddress.text.toString()
                    supplier.Contact_name = txtContact_name.text.toString()
                    supplier.Country = txtCountry.text.toString()
                    supplier.City = txtCity.text.toString()
                    supplier.Post_code = txtPost_code.text.toString()
                    if (!isEditMode)
                        supplierController.addSupplier(supplier)
                    else
                        supplierController.updateSupplier(supplier)

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

    fun deleteSupplier(): Unit{
        try {
            supplierController.removeSupplier(txtId.text.toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess)
                , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }
}
