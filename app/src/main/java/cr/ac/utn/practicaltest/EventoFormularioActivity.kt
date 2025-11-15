package cr.ac.utn.practicaltest

import Controller.EventoController
import Entity.Evento
import Util.Util
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.databinding.LayoutFormEventoBinding
import java.time.LocalDate
import java.time.LocalTime

class EventoFormularioActivity : AppCompatActivity() {
    private lateinit var binding: LayoutFormEventoBinding
    private lateinit var controller: EventoController
    private var currentEvent: Evento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutFormEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = EventoController(this)
        val eventId = intent.getStringExtra("EVENT_ID")
        if (eventId != null) loadEvent(eventId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadEvent(id: String) {
        try {
            currentEvent = controller.getEventById(id)
            currentEvent?.let { e ->
                binding.edtId.setText(e.id)
                binding.edtTitle.setText(e.title)
                binding.edtDescription.setText(e.description)
                binding.edtDate.setText(e.date?.toString() ?: "")
                binding.edtTime.setText(e.time?.toString() ?: "")
                binding.edtLocation.setText(e.location)
                binding.edtOrganizer.setText(e.organizer)
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
        val event = Evento().apply {
            this.id = id
            title = binding.edtTitle.text.toString().trim()
            description = binding.edtDescription.text.toString().trim()
            date = Util.parseStringToDateModern(binding.edtDate.text.toString(), "yyyy-MM-dd")
            time = try { LocalTime.parse(binding.edtTime.text.toString()) } catch (e: Exception) { null }
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
        Util.showDialogCondition(this, getString(R.string.TextDeleteActionQuestion)) {
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
        menuInflater.inflate(R.menu.menu_evento, menu)
        menu?.findItem(R.id.mnu_delete)?.isVisible = currentEvent != null
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> { Util.showDialogCondition(this, getString(R.string.TextSaveActionQuestion)) { saveEvent() }; true }
            R.id.mnu_delete -> { deleteEvent(); true }
            R.id.mnu_cancel -> { finish(); true }
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}