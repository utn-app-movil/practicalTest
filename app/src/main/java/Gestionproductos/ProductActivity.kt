package cr.ac.utn.practicaltest.Gestionproductos

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R

class ProductActivity : AppCompatActivity() {

    private val repository = ProductRepository()
    private var selectedProduct: Product? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_product)  // Se agregará después cuando hagamos el layout

        title = "Gestión de Productos"

        // TODO: Obtener datos recibidos por intent cuando editemos un producto
        // selectedProduct = intent.getSerializableExtra("product") as? Product
    }

    // -----------------------------------------
    // GUARDAR                                 |
    // -----------------------------------------
    private fun saveProduct() {
        showConfirmDialog(
            message = getString(R.string.TextSaveActionQuestion),
            onConfirm = {
                try {
                    val product = Product(
                        id = selectedProduct?.id ?: 0,
                        name = "Producto Ejemplo", // TODO: obtener del EditText
                        description = "Descripción ejemplo", // TODO
                        price = 1000.0, // TODO
                        stock = 10 // TODO
                    )

                    val result = if (selectedProduct == null) {
                        repository.addProduct(product)
                    } else {
                        repository.updateProduct(product)
                    }

                    if (result) {
                        Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_SHORT).show()
                    }

                } catch (ex: Exception) {
                    Toast.makeText(this, getString(R.string.ErrorMsgAdd), Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // -----------------------------------------
    // ELIMINAR                                |
    // -----------------------------------------
    private fun deleteProduct() {
        showConfirmDialog(
            message = getString(R.string.TextDeleteActionQuestion),
            onConfirm = {
                try {
                    val result = selectedProduct?.id?.let { repository.deleteProduct(it) } ?: false

                    if (result) {
                        Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_SHORT).show()
                    }

                } catch (ex: Exception) {
                    Toast.makeText(this, getString(R.string.ErrorMsgRemove), Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    // -------------------------------------------------
    // DIÁLOGO GENÉRICO DE CONFIRMACIÓN DE LOS PRODUCT |
    // -------------------------------------------------
    private fun showConfirmDialog(message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.TextTitleDialogQuestion))
            .setMessage(message)
            .setPositiveButton(getString(R.string.TextYes)) { _, _ -> onConfirm() }
            .setNegativeButton(getString(R.string.TextNo), null)
            .show()
    }
}
