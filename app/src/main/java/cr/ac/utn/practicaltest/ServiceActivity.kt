package cr.ac.utn.practicaltest

import Controller.MovieTicketController
import Data.MemoryDataManager
import Entity.MovieTicket
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate

class ServiceActivity : AppCompatActivity() {
    private lateinit var txtId: EditText
    private lateinit var txtCustomerName: EditText
    private lateinit var txtMovieTitle: EditText
    private lateinit var txtFunctionDate: EditText
    private lateinit var txtQuantity: EditText
    private lateinit var txtUnitPrice: EditText
    private lateinit var listView: ListView
    private lateinit var ticketController: MovieTicketController
    private var selectedTicketId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_service)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ticketController = MovieTicketController(this)

        txtId = findViewById(R.id.idTicket)
        txtCustomerName = findViewById(R.id.clientName)
        txtMovieTitle = findViewById(R.id.movieTitle)
        txtFunctionDate = findViewById(R.id.FunctionDate)
        txtQuantity = findViewById(R.id.TicketQuantity)
        txtUnitPrice = findViewById(R.id.UnitPrice)
        listView = findViewById(R.id.listView)

        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener(View.OnClickListener { view ->
            saveTicket()
        })

        val btnCancel = findViewById<Button>(R.id.btnCancel)
        btnCancel.setOnClickListener(View.OnClickListener { view ->
            cleanScreen()
        })

        val btnDelete = findViewById<Button>(R.id.btn_Delete)
        btnDelete.setOnClickListener(View.OnClickListener { view ->
            deleteTicket()
        })

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            val ticketId = selectedItem.split(" - ")[0]
            selectedTicketId = ticketId
        }

        loadList()
    }

    private fun isValidationData(): Boolean {
        return txtId.text.trim().isNotEmpty()
                && txtCustomerName.text.trim().isNotEmpty()
                && txtMovieTitle.text.trim().isNotEmpty()
                && txtFunctionDate.text.trim().isNotEmpty()
                && txtQuantity.text.trim().isNotEmpty()
                && txtUnitPrice.text.trim().isNotEmpty()
    }

    private fun cleanScreen() {
        txtId.setText("")
        txtCustomerName.setText("")
        txtMovieTitle.setText("")
        txtFunctionDate.setText("")
        txtQuantity.setText("")
        txtUnitPrice.setText("")
        selectedTicketId = null
        Toast.makeText(this, getString(R.string.MsgDeleteSuccess),
            Toast.LENGTH_LONG).show()
    }

    private fun saveTicket() {
        try {
            if (isValidationData()) {
                if (ticketController.getById(txtId.text.toString().trim()) != null) {
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate),
                        Toast.LENGTH_LONG).show()
                } else {
                    val ticket = MovieTicket()
                    ticket.ID = txtId.text.toString().trim()
                    ticket.CustomerName = txtCustomerName.text.toString().trim()
                    ticket.MovieTitle = txtMovieTitle.text.toString().trim()
                    ticket.ShowDate = LocalDate.now()
                    ticket.Quantity = txtQuantity.text.toString().trim().toInt()
                    ticket.UnitPrice = txtUnitPrice.text.toString().trim().toDouble()
                    ticket.calculateTotalPrice()

                    ticketController.addTicket(ticket)
                    cleanScreen()
                    loadList()

                    Toast.makeText(this, getString(R.string.MsgSaveSuccess),
                        Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.ErrorMsgAdd),
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.ErrorMsgAdd),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteTicket() {
        try {
            if (selectedTicketId != null) {
                ticketController.removeTicket(selectedTicketId!!)
                selectedTicketId = null
                cleanScreen()
                loadList()

                Toast.makeText(this, getString(R.string.MsgDeleteSuccess),
                    Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.ErrorMsgRemove),
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, getString(R.string.ErrorMsgRemove),
                Toast.LENGTH_LONG).show()
        }
    }

    private fun loadList() {
        val tickets = MemoryDataManager.getAll()
        val ticketStrings = tickets.map {
            "${it.ID} - ${it.MovieTitle} - ${it.CustomerName} - $${it.TotalPrice}"
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ticketStrings)
        listView.adapter = adapter
    }
}