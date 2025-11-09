package cr.ac.utn.practicaltest

import Controller.ReservationController
import Entity.Reservation
import Entity.ReservationStatus
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ReservationListActivity : AppCompatActivity() {

    private lateinit var rvReservations: RecyclerView
    private lateinit var btnAddReservation: FloatingActionButton
    private lateinit var spinnerFilterStatus: Spinner
    private lateinit var reservationController: ReservationController
    private lateinit var reservationAdapter: ReservationAdapter
    private var allReservations: List<Reservation> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_list)

        // Initialize controller
        reservationController = ReservationController(this)

        // Initialize views
        rvReservations = findViewById(R.id.rvReservations)
        btnAddReservation = findViewById(R.id.btnAddReservation)
        spinnerFilterStatus = findViewById(R.id.spinnerFilterStatus)

        // Setup RecyclerView
        rvReservations.layoutManager = LinearLayoutManager(this)

        // Setup spinner
        setupSpinner()

        // Load reservations
        loadReservations()

        // Setup FAB
        btnAddReservation.setOnClickListener {
            val intent = Intent(this, ReservationFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadReservations()
    }

    private fun setupSpinner() {
        val statuses = mutableListOf("All Statuses")
        statuses.addAll(ReservationStatus.values().map { it.name })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statuses)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilterStatus.adapter = adapter

        spinnerFilterStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterReservations(if (position == 0) null else ReservationStatus.values()[position - 1])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                filterReservations(null)
            }
        }
    }

    private fun loadReservations() {
        allReservations = reservationController.getAllReservations()
        filterReservations(null)
    }

    private fun filterReservations(status: ReservationStatus?) {
        val filteredReservations = if (status == null) {
            allReservations
        } else {
            allReservations.filter { it.Status == status }
        }

        reservationAdapter = ReservationAdapter(filteredReservations) { reservation ->
            val intent = Intent(this, ReservationFormActivity::class.java)
            intent.putExtra("RESERVATION_ID", reservation.ID)
            startActivity(intent)
        }
        rvReservations.adapter = reservationAdapter
    }
}
