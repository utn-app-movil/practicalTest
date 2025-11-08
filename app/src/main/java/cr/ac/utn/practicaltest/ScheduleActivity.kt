package cr.ac.utn.practicaltest

import Data.MemoryScheduleDataManager
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import Entity.Schedule
import java.util.*

class ScheduleActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etDay: EditText
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var etLocation: EditText

    private var scheduleStartTime: String = ""
    private var scheduleEndTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)


        etId = findViewById(R.id.et_schedule_id)
        etDay = findViewById(R.id.et_schedule_day)
        tvStartTime = findViewById(R.id.et_schedule_startTime)
        tvEndTime = findViewById(R.id.et_schedule_endTime)
        etLocation = findViewById(R.id.et_schedule_location)


        tvStartTime.setOnClickListener {
            showTimePicker { hour, minute ->
                scheduleStartTime = String.format("%02d:%02d", hour, minute)
                tvStartTime.text = scheduleStartTime
            }
        }


        tvEndTime.setOnClickListener {
            showTimePicker { hour, minute ->
                scheduleEndTime = String.format("%02d:%02d", hour, minute)
                tvEndTime.text = scheduleEndTime
            }
        }
    }


    private fun showTimePicker(onTimeSelected: (Int, Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            onTimeSelected(selectedHour, selectedMinute)
        }, hour, minute, true).show()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.mnu_save -> {
                saveSchedule()
                true
            }
            R.id.mnu_delete -> {
                deleteSchedule()
                true
            }
            R.id.mnu_cancel -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun saveSchedule() {
        val id = etId.text.toString()
        val day = etDay.text.toString()
        val location = etLocation.text.toString()

        if (id.isBlank() || day.isBlank() || scheduleStartTime.isBlank() || scheduleEndTime.isBlank() || location.isBlank()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val schedule = Schedule(id, day, scheduleStartTime, scheduleEndTime, location)


        val exists = MemoryScheduleDataManager.getById(id)
        val success = if (exists != null) {
            MemoryScheduleDataManager.update(schedule)
        } else {
            MemoryScheduleDataManager.add(schedule)
        }

        if (success) {
            Toast.makeText(this, "Horario guardado correctamente", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "Error al guardar el horario", Toast.LENGTH_SHORT).show()
        }
    }


    private fun deleteSchedule() {
        val id = etId.text.toString()
        if (id.isBlank()) {
            Toast.makeText(this, "Ingrese el ID del horario a eliminar", Toast.LENGTH_SHORT).show()
            return
        }

        val success = MemoryScheduleDataManager.remove(id)
        if (success) {
            Toast.makeText(this, "Horario eliminado", Toast.LENGTH_SHORT).show()
            clearFields()
        } else {
            Toast.makeText(this, "No se encontró el horario con ese ID", Toast.LENGTH_SHORT).show()
        }
    }


    private fun clearFields() {
        etId.text.clear()
        etDay.text.clear()
        tvStartTime.text = "Seleccionar hora"
        tvEndTime.text = "Seleccionar hora"
        etLocation.text.clear()
        scheduleStartTime = ""
        scheduleEndTime = ""
    }
}