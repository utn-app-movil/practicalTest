package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import Data.MemoryScheduleDataManager
import Data.MemoryVolunteeringActivityDataManager
import Entity.VolunteeringActivity


class VolunteerActivity : AppCompatActivity() {

    private lateinit var etId: EditText
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var spinnerSchedule: Spinner

    private var selectedScheduleId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volunteer)

        etId = findViewById(R.id.et_activity_id)
        etTitle = findViewById(R.id.et_activity_title)
        etDescription = findViewById(R.id.et_activity_description)
        spinnerSchedule = findViewById(R.id.spinner_schedule)


        val schedules = MemoryScheduleDataManager.getAll()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, schedules.map { it.ScheduleInfo() })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSchedule.adapter = adapter

        spinnerSchedule.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                selectedScheduleId = schedules[position].ID
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_save -> saveActivity()
            R.id.mnu_delete -> deleteActivity()
            R.id.mnu_cancel -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveActivity() {
        val id = etId.text.toString().trim()
        val title = etTitle.text.toString().trim()
        val description = etDescription.text.toString().trim()

        if (id.isEmpty() || title.isEmpty() || description.isEmpty() || selectedScheduleId == null) {
            Toast.makeText(this, R.string.msg_fill_all_fields, Toast.LENGTH_SHORT).show()
            return
        }

        val schedule = MemoryScheduleDataManager.getById(selectedScheduleId!!)!!
        val activity = VolunteeringActivity(id, title, description, schedule, mutableListOf())

        val exists = MemoryVolunteeringActivityDataManager.getById(id)
        val success = if (exists != null) {
            MemoryVolunteeringActivityDataManager.update(activity)
        } else {
            MemoryVolunteeringActivityDataManager.add(activity)
        }

        if (success) {
            Toast.makeText(this, R.string.msg_activity_saved, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.msg_activity_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteActivity() {
        val id = etId.text.toString().trim()
        val success = MemoryVolunteeringActivityDataManager.remove(id)
        if (success) {
            Toast.makeText(this, R.string.msg_activity_deleted, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, R.string.msg_activity_not_found, Toast.LENGTH_SHORT).show()
        }
    }
}