package cr.ac.utn.practicaltest

import Controller.ReceiptController
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import Entity.Receipt
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ReceiptActivity: AppCompatActivity() {
     private lateinit var TxtIdR: EditText
     private lateinit var Txtnumber: EditText
     private lateinit var Txtclient: EditText
     private lateinit var Txamount: EditText
     private lateinit var txtdate: EditText
     private lateinit var receiptController: ReceiptController

     override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_receipt)
          receiptController = ReceiptController(this)


          TxtIdR = findViewById(R.id.TxtIdReceipt)
          Txtnumber = findViewById(R.id.TxtReceipt_number)
          Txtclient = findViewById(R.id.TxtReceiptClient)
          Txamount = findViewById(R.id.TxtReceiptAmount)
          txtdate = findViewById(R.id.TxtDate)


          val btn_saveReceipt = findViewById<Button>(R.id.Save)


          btn_saveReceipt.setOnClickListener {
               saveReceipt()
          }
     }

     private fun saveReceipt() {
          try {
               val id = TxtIdR.text.toString()
               val number = Txtnumber.text.toString()
               val client = Txtclient.text.toString()
               val amount = Txamount.text.toString().toDoubleOrNull() ?: 0.0
               val date = try {
                    LocalDate.parse(txtdate.text.toString(), DateTimeFormatter.ISO_LOCAL_DATE)
               } catch (e: Exception) {
                    LocalDate.now()
               }


               if (id.isBlank() || number.isBlank() || client.isBlank()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return
               }

               val newReceipt = Receipt(id, number, client, amount, date)
               receiptController.addReceipt(newReceipt)
               Toast.makeText(this, "Receipt saved successfully", Toast.LENGTH_SHORT).show()
               clearForm()
          } catch (e: Exception) {
               Toast.makeText(this, "Error saving receipt:  ${e.message}", Toast.LENGTH_LONG).show()
          }
     }
     private fun clearForm() {
          TxtIdR.text.clear()
          Txtnumber.text.clear()
          Txtclient.text.clear()
          Txamount.text.clear()
          txtdate.text.clear()
     }
}
