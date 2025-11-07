package cr.ac.utn.practicaltest

import Controller.EventController
import Entity.Event
import Util.Util
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.databinding.ActivityEventBinding
import java.time.LocalDate
import java.time.LocalTime
class EventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEventBinding
    private lateinit var controller: EventController
    private var currentEvent: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = EventController(this)

        val eventId = intent.getStringExtra("EVENT_ID")
        if (eventId != null) {
            loadEvent(eventId)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadEvent(id: String) {
        try {
            currentEvent = controller.getEventById(id)
            currentEvent?.let { event ->
                binding.edtId.setText(event.id)
                binding.edtTitle.setText(event.title)
                binding.edtDescription.setText(event.description)
                binding.edtDate.setText(event.date?.toString() ?: "")
                binding.edtTime.setText(event.time?.toString() ?: "")
                binding.edtLocation.setText(event.location)
                binding.edtOrganizer.setText(event.organizer)

                binding.edtId.isEnabled = false
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun saveEvent() {
        val id = binding.edtId.text.toString().trim()
        if (id.isEmpty()) {
            Toast.makeText(this, "El ID es obligatorio", Toast.LENGTH_SHORT).show()
            return
        }

        val event = Event().apply {
            this.id = id
            title = binding.edtTitle.text.toString().trim()
            description = binding.edtDescription.text.toString().trim()
            date = Util.parseStringToDateModern(binding.edtDate.text.toString(), "yyyy-MM-dd")
            time = try {
                LocalTime.parse(binding.edtTime.text.toString())
            } catch (e: Exception) { null }
            location = binding.edtLocation.text.toString().trim()
            organizer = binding.edtOrganizer.text.toString().trim()
        }

        try {
            if (currentEvent == null) {
                controller.addEvent(event)
                Toast.makeText(this, "Evento creado con éxito", Toast.LENGTH_SHORT).show()
            } else {
                controller.updateEvent(event)
                Toast.makeText(this, "Evento actualizado", Toast.LENGTH_SHORT).show()
            }
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteEvent() {
        Util.showDialogCondition(this, "¿Desea eliminar este evento?") {
            try {
                controller.deleteEvent(currentEvent!!.id)
                Toast.makeText(this, "Evento eliminado", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_event, menu)
        menu?.findItem(R.id.mnu_delete)?.isVisible = currentEvent != null
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> { Util.showDialogCondition(this, "¿Desea guardar?") { saveEvent() }; true }
            R.id.mnu_delete -> { deleteEvent(); true }
            R.id.mnu_cancel -> { finish(); true }
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}