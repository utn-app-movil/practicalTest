package cr.ac.utn.practicaltest

import Controller.ProductController
import Entity.Product
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ProductActivity : AppCompatActivity() {

    private lateinit var controller: ProductController

    private lateinit var txtId: EditText
    private lateinit var txtName: EditText
    private lateinit var txtPrice: EditText
    private lateinit var txtStock: EditText
    private lateinit var txtDescription: EditText
    private lateinit var lblStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product)

        controller = ProductController(this)

        txtId = findViewById(R.id.txtId_product)
        txtName = findViewById(R.id.txtName_product)
        txtPrice = findViewById(R.id.txtPrice_product)
        txtStock = findViewById(R.id.txtStock_product)
        txtDescription = findViewById(R.id.txtDescription_product)
        lblStatus = findViewById(R.id.lblStatus_product)

        // Si venimos desde el Main con un ID para buscar (findById)
        val productIdFromMain = intent.getStringExtra("productId") ?: ""
        if (productIdFromMain.isNotBlank()) {
            txtId.setText(productIdFromMain)
            loadById(productIdFromMain)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu) // ya existe en tu ZIP
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_save -> {
                saveOrUpdate()
                return true
            }
            R.id.mnu_delete -> {
                delete()
                return true
            }
            R.id.mnu_cancel -> {
                clearForm()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveOrUpdate() {
        try {
            val id = txtId.text.toString().trim()
            val name = txtName.text.toString().trim()
            val price = txtPrice.text.toString().toDoubleOrNull() ?: 0.0
            val stock = txtStock.text.toString().toIntOrNull() ?: 0
            val desc = txtDescription.text.toString().trim()

            val p = Product().apply {
                ID = id
                Name = name
                Price = price
                Stock = stock
                Description = desc
            }

            // si existe -> update, si no -> add
            val exists = controller.getById(id) != null
            if (exists) {
                controller.update(p)
                Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_SHORT).show()
                lblStatus.text = "Actualizado: $id"
            } else {
                controller.add(p)
                Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_SHORT).show()
                lblStatus.text = "Guardado: $id"
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: getString(R.string.ErrorMsgAdd), Toast.LENGTH_SHORT).show()
        }
    }

    private fun delete() {
        try {
            val id = txtId.text.toString().trim()
            if (id.isBlank()) {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_SHORT).show()
                return
            }
            controller.remove(id)
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_SHORT).show()
            lblStatus.text = "Eliminado: $id"
            clearForm()
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: getString(R.string.ErrorMsgRemove), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadById(id: String) {
        try {
            val p = controller.getById(id)
            if (p == null) {
                lblStatus.text = "No encontrado: $id"
                return
            }
            txtId.setText(p.ID)
            txtName.setText(p.Name)
            txtPrice.setText(p.Price.toString())
            txtStock.setText(p.Stock.toString())
            txtDescription.setText(p.Description)
            lblStatus.text = "Cargado: $id"
        } catch (e: Exception) {
            Toast.makeText(this, e.message ?: getString(R.string.ErrorMsgGetById), Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        txtId.setText("")
        txtName.setText("")
        txtPrice.setText("")
        txtStock.setText("")
        txtDescription.setText("")
        lblStatus.text = ""
    }
}
