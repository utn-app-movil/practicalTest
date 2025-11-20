package cr.ac.utn.practicaltest

import Controller.ReceiptController
import Entity.Receipt
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterReceiptActivity: AppCompatActivity() {
    // ----- Campos visuales -----
    private lateinit var txtReceiptID: EditText
    private lateinit var txtEmitterFiscID: EditText
    private lateinit var txtReceiverFiscID: EditText
    private lateinit var txtReceiverName: EditText
    private lateinit var txtEmitterName: EditText
    private lateinit var txtReceiptDesc: EditText
    private lateinit var txtTaxRate: EditText

    // ----- Controlador -----
    private lateinit var receiptController: ReceiptController
    private var isEditMode: Boolean = false
    private lateinit var menuItemDelete: MenuItem

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_receipt)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización
        receiptController = ReceiptController(this)

        txtReceiptID = findViewById(R.id.txtReceiptID)
        txtEmitterFiscID = findViewById(R.id.txtEmitterFiscID)
        txtReceiverFiscID = findViewById(R.id.txtReceiverFiscID)
        txtReceiverName = findViewById(R.id.txtReceiverName)
        txtEmitterName = findViewById(R.id.txtEmitterName)
        txtReceiptDesc = findViewById(R.id.txtReceiptDesc)
        txtTaxRate = findViewById(R.id.txtTaxRate)

        // Botón buscar (opcional)
        val btnSearch = findViewById<ImageButton>(R.id.btnSearchRegistry)
        btnSearch?.setOnClickListener(View.OnClickListener { view ->
            searchReceipt(txtReceiptID.text.trim().toString())
        })
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
                if (isEditMode) {
                    Util.showDialogCondition(this, getString(R.string.TextSaveActionQuestion)) { saveReceipt() }
                } else {
                    saveReceipt()
                }
                true
            }

            R.id.mnu_delete -> {
                Util.showDialogCondition(this, getString(R.string.TextDeleteActionQuestion)) { deleteReceipt() }
                true
            }

            R.id.mnu_cancel -> {
                cleanScreen()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    // ----- Limpia pantalla -----
    private fun cleanScreen() {
        isEditMode = false
        txtReceiptID.isEnabled = true
        txtReceiptID.setText("")
        txtEmitterFiscID.setText("")
        txtReceiverFiscID.setText("")
        txtReceiverName.setText("")
        txtEmitterName.setText("")
        txtReceiptDesc.setText("")
        txtTaxRate.setText("")
        invalidateOptionsMenu()
    }

    // ----- Buscar factura -----
    private fun searchReceipt(receiptId: String) {
        try {
            val receipt = receiptController.getReceiptById(receiptId)
            if (receipt != null) {
                isEditMode = true
                txtReceiptID.setText(receipt.ReceiptId)
                txtReceiptID.isEnabled = false
                txtEmitterName.setText(receipt.EmitterName)
                txtReceiverName.setText(receipt.ReceiverName)
                txtReceiverFiscID.setText(receipt.ReceiverFiscalId)
                txtEmitterFiscID.setText(receipt.EmitterFiscalId)
                txtReceiptDesc.setText(receipt.ReceiptDescription)
                txtTaxRate.setText(receipt.TaxRate.toString())
                menuItemDelete.isVisible = true
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            cleanScreen()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // ----- Guardar factura -----
    private fun saveReceipt() {
        try {
            if (isValidationData()) {
                if (receiptController.getReceiptById(txtReceiptID.text.toString().trim()) != null && !isEditMode) {
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_LONG).show()
                } else {
                    val receipt = Receipt()
                    receipt.ReceiptId = txtReceiptID.text.toString()
                    receipt.EmitterName = txtEmitterName.text.toString()
                    receipt.ReceiverName = txtReceiverName.text.toString()
                    receipt.EmitterFiscalId = txtEmitterFiscID.text.toString()
                    receipt.ReceiverFiscalId = txtReceiverFiscID.text.toString()
                    receipt.ReceiptDescription = txtReceiptDesc.text.toString()
                    receipt.TaxRate = txtTaxRate.text.toString().toDoubleOrNull() ?: 0.0

                    if (!isEditMode)
                        receiptController.addReceipt(receipt)
                    else
                        receiptController.updateReceipt(receipt)

                    cleanScreen()
                    Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.MsgIncompleteData), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // ----- Validar datos -----
    private fun isValidationData(): Boolean {
        return txtReceiptID.text.trim().isNotEmpty() &&
                txtEmitterFiscID.text.trim().isNotEmpty() &&
                txtReceiverFiscID.text.trim().isNotEmpty() &&
                txtReceiverName.text.trim().isNotEmpty() &&
                txtEmitterName.text.trim().isNotEmpty() &&
                txtReceiptDesc.text.trim().isNotEmpty() &&
                txtTaxRate.text.trim().isNotEmpty()
    }

    // ----- Eliminar factura -----
    private fun deleteReceipt() {
        try {
            receiptController.removeReceipt(txtReceiptID.text.toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
