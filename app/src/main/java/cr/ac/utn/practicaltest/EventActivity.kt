// src/main/java/cr/ac/utn/practicaltest/ui/EventActivity.kt
package cr.ac.utn.practicaltest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R
import cr.ac.utn.practicaltest.controller.EventController
import cr.ac.utn.practicaltest.data.MemoryEventDataManager
import cr.ac.utn.practicaltest.model.Event
import cr.ac.utn.practicaltest.ui.EventAdapter

class EventActivity : AppCompatActivity() {

    private lateinit var controller: EventController
    private lateinit var adapter: EventAdapter
    private lateinit var rvEvents: RecyclerView
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etDate: EditText
    private lateinit var etLocation: EditText
    private lateinit var btnSave: Button
    private var editingEventId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val dataManager = MemoryEventDataManager()
        controller = EventController(dataManager)

        initViews()
        setupRecyclerView()
        setupSaveButton()
        loadEvents()
    }

    private fun initViews() {
        rvEvents = findViewById(R.id.rvEvents)
        etTitle = findViewById(R.id.etTitle)
        etDescription = findViewById(R.id.etDescription)
        etDate = findViewById(R.id.etDate)
        etLocation = findViewById(R.id.etLocation)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setupRecyclerView() {
        adapter = EventAdapter(
            onEdit = { startEdit(it) },
            onDelete = { deleteEvent(it) }
        )
        rvEvents.layoutManager = LinearLayoutManager(this)
        rvEvents.adapter = adapter
    }

    private fun setupSaveButton() {
        btnSave.setOnClickListener { saveEvent() }
    }

    private fun saveEvent() {
        val title = etTitle.text.toString().trim()
        val desc = etDescription.text.toString().trim()
        val date = etDate.text.toString().trim()
        val location = etLocation.text.toString().trim()

        if (title.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Título y fecha son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (editingEventId == null) {
            controller.createEvent(title, desc, date, location)
        } else {
            controller.updateEvent(editingEventId!!, title, desc, date, location)
            editingEventId = null
            btnSave.text = "Guardar Evento"
        }

        clearForm()
        loadEvents()
    }

    private fun startEdit(event: Event) {
        editingEventId = event.id
        etTitle.setText(event.title)
        etDescription.setText(event.description)
        etDate.setText(event.date)
        etLocation.setText(event.location)
        btnSave.text = "Actualizar Evento"
    }

    private fun deleteEvent(event: Event) {
        controller.deleteEvent(event.id)
        loadEvents()
    }

    private fun loadEvents() {
        adapter.submitList(controller.getAllEvents())
    }

    private fun clearForm() {
        etTitle.text.clear()
        etDescription.text.clear()
        etDate.text.clear()
        etLocation.text.clear()
    }
}