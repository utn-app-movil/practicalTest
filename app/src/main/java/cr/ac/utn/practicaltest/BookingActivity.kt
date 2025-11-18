package cr.ac.utn.practicaltest

import Controller.BookingController
import Controller.PersonController
import Entity.Booking
import Entity.Person
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDateTime
import java.util.Calendar

class BookingActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    // references to UI controls
    private lateinit var txtIdBooking: EditText
    private lateinit var txtSpaceName: EditText
    private lateinit var txtSpaceType: EditText
    private lateinit var tvBookingDateTime: TextView
    private lateinit var tvBookingEndDateTime: TextView
    private lateinit var spStatus: Spinner
    private lateinit var txtPersonId: EditText
    private lateinit var tvPersonFullName: TextView
    private lateinit var btnSaveBooking: Button

    // controllers
    private lateinit var bookingController: BookingController
    private lateinit var personController: PersonController

    // state mode
    private var IsEditMode: Boolean = false
    private lateinit var menuItemDelete: MenuItem

    // to know if we are selecting start or end datetime
    private var isSelectingStart: Boolean = true

    // datetime variables for start
    private var startYear: Int = 0
    private var startMonth: Int = 0  // 0-based as in Calendar
    private var startDay: Int = 0
    private var startHour: Int = 0
    private var startMinute: Int = 0

    // datetime variables for end
    private var endYear: Int = 0
    private var endMonth: Int = 0   // 0-based
    private var endDay: Int = 0
    private var endHour: Int = 0
    private var endMinute: Int = 0

    private var selectedStatus: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initialize controllers
        bookingController = BookingController(this)
        personController = PersonController(this)

        // view references
        txtIdBooking        = findViewById(R.id.txtID_Booking)
        txtSpaceName        = findViewById(R.id.txtSpaceName_Booking)
        txtSpaceType        = findViewById(R.id.txtSpaceType_Booking)
        tvBookingDateTime   = findViewById(R.id.tvBookingDateTime)
        tvBookingEndDateTime= findViewById(R.id.tvBookingEndDateTime)
        spStatus            = findViewById(R.id.spStatus_Booking)
        txtPersonId         = findViewById(R.id.txtPersonId_Booking)
        tvPersonFullName    = findViewById(R.id.tvPersonFullName_Booking)
        btnSaveBooking      = findViewById(R.id.btnSave_Booking)

        resetDateTimes()

        // search booking by ID
        val btnSearchBooking = findViewById<ImageButton>(R.id.btnSearchId_Booking)
        btnSearchBooking.setOnClickListener {
            searchBooking(txtIdBooking.text.trim().toString())
        }

        // search person by ID
        val btnSearchPerson = findViewById<ImageButton>(R.id.btnSearchPerson_Booking)
        btnSearchPerson.setOnClickListener {
            searchPerson(txtPersonId.text.trim().toString())
        }

        // select start datetime
        val btnSelectStartDateTime = findViewById<ImageButton>(R.id.btnSelectStartDateTime)
        btnSelectStartDateTime.setOnClickListener {
            isSelectingStart = true
            showDatePickerDialog()
        }

        // select start datetime
        val btnSelectEndDateTime = findViewById<ImageButton>(R.id.btnSelectEndDateTime)
        btnSelectEndDateTime.setOnClickListener {
            isSelectingStart = false
            showDatePickerDialog()
        }

        // button save booking
        btnSaveBooking.setOnClickListener {
            if (IsEditMode) {
                Util.Util.showDialogCondition(
                    this,
                    getString(R.string.TextSaveActionQuestion)
                ) {
                    saveBooking()
                }
            } else {
                saveBooking()
            }
        }

        // status combo (uses strings.xml with Spinner)
        // and it uses booking_status_array defined in arrays.xml, such as "Pending", "Confirmed", "Cancelled"
        val bookingStatusNames = resources.getStringArray(R.array.booking_status_array)
        // Create adapter for Spinner
        val statusAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            bookingStatusNames
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spStatus.adapter = statusAdapter
        // Default value (first item)
        spStatus.setSelection(0)
        selectedStatus = bookingStatusNames[0]
        // Selection listener
        spStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = bookingStatusNames[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedStatus = bookingStatusNames[0]
            }
        }
    }

    private fun resetDateTimes() {
        val calendar = Calendar.getInstance()

        // start datetime
        startYear = calendar.get(Calendar.YEAR)
        startMonth = calendar.get(Calendar.MONTH)
        startDay = calendar.get(Calendar.DAY_OF_MONTH)
        startHour = calendar.get(Calendar.HOUR_OF_DAY)
        startMinute = calendar.get(Calendar.MINUTE)
        tvBookingDateTime.text = getDateTimeString(
            startDay,
            startMonth + 1,
            startYear,
            startHour,
            startMinute
        )

        // end datetime (by default, one hour later)
        calendar.add(Calendar.HOUR_OF_DAY, 1)
        endYear = calendar.get(Calendar.YEAR)
        endMonth = calendar.get(Calendar.MONTH)
        endDay = calendar.get(Calendar.DAY_OF_MONTH)
        endHour = calendar.get(Calendar.HOUR_OF_DAY)
        endMinute = calendar.get(Calendar.MINUTE)
        tvBookingEndDateTime.text = getDateTimeString(
            endDay,
            endMonth + 1,
            endYear,
            endHour,
            endMinute
        )
    }

    private fun getDateTimeString(
        dayValue: Int,
        monthValue: Int,
        yearValue: Int,
        hourValue: Int,
        minuteValue: Int
    ): String {
        val dd = dayValue.toString().padStart(2, '0')
        val mm = monthValue.toString().padStart(2, '0')
        val hh = hourValue.toString().padStart(2, '0')
        val min = minuteValue.toString().padStart(2, '0')
        return "$dd/$mm/$yearValue $hh:$min"
    }

    private fun showDatePickerDialog() {
        val year = if (isSelectingStart) startYear else endYear
        val month = if (isSelectingStart) startMonth else endMonth
        val day = if (isSelectingStart) startDay else endDay
        val dialog = DatePickerDialog(this@BookingActivity, this, year, month, day)
        dialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // month is 0-based
        if (isSelectingStart) {
            startYear = year
            startMonth = month
            startDay = dayOfMonth
        } else {
            endYear = year
            endMonth = month
            endDay = dayOfMonth
        }

        // after selecting date, show the TimePicker
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timeDialog = TimePickerDialog(
            this@BookingActivity,
            this,
            hour,
            minute,
            true
        )
        timeDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (isSelectingStart) {
            startHour = hourOfDay
            startMinute = minute
            tvBookingDateTime.text = getDateTimeString(
                startDay,
                startMonth + 1,
                startYear,
                startHour,
                startMinute
            )
        } else {
            endHour = hourOfDay
            endMinute = minute
            tvBookingEndDateTime.text = getDateTimeString(
                endDay,
                endMonth + 1,
                endYear,
                endHour,
                endMinute
            )
        }
    }

    private fun cleanScreen() {
        IsEditMode = false
        txtIdBooking.isEnabled = true
        txtIdBooking.setText("")
        txtSpaceName.setText("")
        txtSpaceType.setText("")
        spStatus.setSelection(0)
        txtPersonId.setText("")
        tvPersonFullName.text = ""
        resetDateTimes()
        invalidateOptionsMenu()
    }

    private fun isValidationData(): Boolean {
        return txtIdBooking.text.trim().isNotEmpty()
                && txtSpaceName.text.trim().isNotEmpty()
                && txtSpaceType.text.trim().isNotEmpty()
                && tvBookingDateTime.text.trim().isNotEmpty()
                && tvBookingEndDateTime.text.trim().isNotEmpty()
                && txtPersonId.text.trim().isNotEmpty()
    }

    private fun saveBooking() {
        try {
            if (!isValidationData()) {
                Toast.makeText(
                    this,
                    getString(R.string.MsgIncompleteData),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            // build LocalDateTime for start and end
            val startDateTime = LocalDateTime.of(
                startYear,
                startMonth + 1,
                startDay,
                startHour,
                startMinute
            )

            val endDateTime = LocalDateTime.of(
                endYear,
                endMonth + 1,
                endDay,
                endHour,
                endMinute
            )

            if (endDateTime.isBefore(startDateTime)) {
                Toast.makeText(
                    this,
                    getString(R.string.MsgEndDateBeforeStart),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            // static person (who made the booking)
            // instead of using PersonController
            val person = Person().apply {
                // if the ID is received
                ID = if (txtPersonId.text.trim().isNotEmpty()) {
                    txtPersonId.text.trim().toString()
                } else {
                    "TEST001"
                }
                // static name
                Name = "Persona"
                FLastName = "Prueba"
                SLastName = "Demo"
            }
            // update the TextView so something is shown on screen
            tvPersonFullName.text = person.FullName()

            // check for duplicates when not in edit mode
            if (bookingController.getById(txtIdBooking.text.toString().trim()) != null
                && !IsEditMode
            ) {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDuplicateDate),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val booking = Booking()
            booking.IDBooking = txtIdBooking.text.toString()
            booking.SpaceName = txtSpaceName.text.toString()
            booking.SpaceType = txtSpaceType.text.toString()
            booking.BookingDateTime = startDateTime
            booking.BookingEndDateTime = endDateTime
            booking.Status = selectedStatus
            booking.Person = person   // static person used here

            if (!IsEditMode)
                bookingController.addBooking(booking)
            else
                bookingController.updateBooking(booking)

            cleanScreen()
            Toast.makeText(
                this,
                getString(R.string.MsgSaveSuccess),
                Toast.LENGTH_LONG
            ).show()

        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun searchBooking(id: String) {
        try {
            val booking = bookingController.getById(id)
            if (booking != null) {
                IsEditMode = true
                txtIdBooking.isEnabled = false

                txtIdBooking.setText(booking.IDBooking)
                txtSpaceName.setText(booking.SpaceName)
                txtSpaceType.setText(booking.SpaceType)

                // load start and end datetimes
                val start = booking.BookingDateTime
                val end = booking.BookingEndDateTime

                startYear = start.year
                startMonth = start.monthValue - 1
                startDay = start.dayOfMonth
                startHour = start.hour
                startMinute = start.minute
                tvBookingDateTime.text = getDateTimeString(
                    startDay,
                    startMonth + 1,
                    startYear,
                    startHour,
                    startMinute
                )

                endYear = end.year
                endMonth = end.monthValue - 1
                endDay = end.dayOfMonth
                endHour = end.hour
                endMinute = end.minute
                tvBookingEndDateTime.text = getDateTimeString(
                    endDay,
                    endMonth + 1,
                    endYear,
                    endHour,
                    endMinute
                )

                // set status in spinner (match by text)
                val statusValue = booking.Status
                val adapter = spStatus.adapter
                var index = 0
                for (i in 0 until adapter.count) {
                    if (adapter.getItem(i)?.toString().equals(statusValue, ignoreCase = true)) {
                        index = i
                        break
                    }
                }
                spStatus.setSelection(index)

                // person
                val person = booking.Person
                if (person != null) {
                    txtPersonId.setText(person.ID)
                    tvPersonFullName.text = person.FullName()
                } else {
                    txtPersonId.setText("")
                    tvPersonFullName.text = "(sin seleccionar)"
                }

                invalidateOptionsMenu()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun searchPerson(id: String) {
        try {
            val person = personController.getById(id)
            if (person != null) {
                txtPersonId.setText(person.ID)
                tvPersonFullName.text = person.FullName()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteBooking() {
        try {
            bookingController.removeBooking(txtIdBooking.text.trim().toString())
            cleanScreen()
            Toast.makeText(
                this,
                getString(R.string.MsgDeleteSuccess),
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    // CRUD menu

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = IsEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                if (IsEditMode) {
                    Util.Util.showDialogCondition(
                        this,
                        getString(R.string.TextSaveActionQuestion)
                    ) {
                        saveBooking()
                    }
                } else {
                    saveBooking()
                }
                true
            }
            R.id.mnu_delete -> {
                Util.Util.showDialogCondition(
                    this,
                    getString(R.string.TextDeleteActionQuestion)
                ) {
                    deleteBooking()
                }
                true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
