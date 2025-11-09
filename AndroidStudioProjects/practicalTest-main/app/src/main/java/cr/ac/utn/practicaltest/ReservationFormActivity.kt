package cr.ac.utn.practicaltest

import Controller.ReservationController
import Controller.SpaceController
import Controller.PersonController
import Entity.Reservation
import Entity.ReservationStatus
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDateTime
import java.util.Calendar
import java.util.UUID

class ReservationFormActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var spinnerSpace: Spinner
    private lateinit var spinnerPerson: Spinner
    private lateinit var etStartDateTime: TextInputEditText
    private lateinit var etEndDateTime: TextInputEditText
    private lateinit var etPurpose: TextInputEditText
    private lateinit var etNumberOfAttendees: TextInputEditText
    private lateinit var etNotes: TextInputEditText
    private lateinit var tvPricePreview: TextView
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private lateinit var reservationController: ReservationController
    private lateinit var spaceController: SpaceController
    private lateinit var personController: PersonController

    private var reservationId: String? = null
    private var isEditMode = false
    private var startDateTime: LocalDateTime = LocalDateTime.now()
    private var endDateTime: LocalDateTime = LocalDateTime.now().plusHours(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_form)

        // Initialize controllers
        reservationController = ReservationController(this)
        spaceController = SpaceController(this)
        personController = PersonController(this)

        // Initialize views
        tvTitle = findViewById(R.id.tvTitle)
        spinnerSpace = findViewById(R.id.spinnerSpace)
        spinnerPerson = findViewById(R.id.spinnerPerson)
        etStartDateTime = findViewById(R.id.etStartDateTime)
        etEndDateTime = findViewById(R.id.etEndDateTime)
        etPurpose = findViewById(R.id.etPurpose)
        etNumberOfAttendees = findViewById(R.id.etNumberOfAttendees)
        etNotes = findViewById(R.id.etNotes)
        tvPricePreview = findViewById(R.id.tvPricePreview)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        // Setup spinners
        setupSpinners()

        // Setup date time pickers
        etStartDateTime.setOnClickListener { showDateTimePicker(true) }
        etEndDateTime.setOnClickListener { showDateTimePicker(false) }

        // Update displays
        updateDateTimeDisplays()

        // Check if editing
        reservationId = intent.getStringExtra("RESERVATION_ID")
        if (reservationId != null) {
            isEditMode = true
            tvTitle.text = "Editar Reserva"
            loadReservation(reservationId!!)
        } else {
            tvTitle.text = "Agregar Reserva"
        }

        // Setup buttons
        btnSave.setOnClickListener { saveReservation() }
        btnCancel.setOnClickListener { finish() }
    }

    private fun setupSpinners() {
        // Setup space spinner
        val spaces = spaceController.getAllSpaces()
        val spaceNames = spaces.map { "${it.Name} (${it.Type.name})" }
        val spaceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spaceNames)
        spaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSpace.adapter = spaceAdapter

        // Setup person spinner
        val persons = personController.getAll()
        val personNames = persons.map { "${it.Identification} - ${it.Name}" }
        val personAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, personNames)
        personAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPerson.adapter = personAdapter
    }

    private fun showDateTimePicker(isStart: Boolean) {
        val calendar = Calendar.getInstance()

        DatePickerDialog(this, { _, year, month, day ->
            TimePickerDialog(this, { _, hour, minute ->
                if (isStart) {
                    startDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                } else {
                    endDateTime = LocalDateTime.of(year, month + 1, day, hour, minute)
                }
                updateDateTimeDisplays()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    private fun updateDateTimeDisplays() {
        etStartDateTime.setText(startDateTime.toString())
        etEndDateTime.setText(endDateTime.toString())
        calculatePrice()
    }

    private fun calculatePrice() {
        val spaces = spaceController.getAllSpaces()
        if (spinnerSpace.selectedItemPosition < spaces.size) {
            val space = spaces[spinnerSpace.selectedItemPosition]
            val hours = java.time.Duration.between(startDateTime, endDateTime).toHours()
            val price = hours * space.PricePerHour
            tvPricePreview.text = "Precio estimado: $${price} (${hours} horas × $${space.PricePerHour}/hora)"
        }
    }

    private fun loadReservation(id: String) {
        val reservation = reservationController.getReservationById(id)
        if (reservation != null) {
            etPurpose.setText(reservation.Purpose)
            etNumberOfAttendees.setText(reservation.NumberOfAttendees.toString())
            etNotes.setText(reservation.Notes)
            startDateTime = reservation.StartDateTime
            endDateTime = reservation.EndDateTime
            updateDateTimeDisplays()
        }
    }

    private fun saveReservation() {
        if (etPurpose.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor ingrese el propósito", Toast.LENGTH_SHORT).show()
            return
        }

        if (etNumberOfAttendees.text.isNullOrBlank()) {
            Toast.makeText(this, "Por favor ingrese el número de asistentes", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val spaces = spaceController.getAllSpaces()
            val persons = personController.getAll()

            if (spinnerSpace.selectedItemPosition >= spaces.size) {
                Toast.makeText(this, "Por favor seleccione un espacio válido", Toast.LENGTH_SHORT).show()
                return
            }

            if (spinnerPerson.selectedItemPosition >= persons.size) {
                Toast.makeText(this, "Por favor seleccione una persona válida", Toast.LENGTH_SHORT).show()
                return
            }

            val selectedSpace = spaces[spinnerSpace.selectedItemPosition]
            val selectedPerson = persons[spinnerPerson.selectedItemPosition]
            val hours = java.time.Duration.between(startDateTime, endDateTime).toHours()
            val totalPrice = hours * selectedSpace.PricePerHour

            val reservation = Reservation(
                id = reservationId ?: UUID.randomUUID().toString(),
                spaceId = selectedSpace.ID,
                personId = selectedPerson.ID,
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                status = ReservationStatus.PENDIENTE,
                purpose = etPurpose.text.toString(),
                numberOfAttendees = etNumberOfAttendees.text.toString().toInt(),
                totalPrice = totalPrice,
                notes = etNotes.text?.toString() ?: ""
            )

            val success = if (isEditMode) {
                reservationController.updateReservation(reservation)
            } else {
                reservationController.addReservation(reservation)
            }

            if (success) {
                Toast.makeText(
                    this,
                    if (isEditMode) "Reserva actualizada" else "Reserva creada",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
