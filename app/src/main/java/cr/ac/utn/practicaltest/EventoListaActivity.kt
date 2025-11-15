package cr.ac.utn.practicaltest

import Controller.EventoController
import Util.Util
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cr.ac.utn.practicaltest.adapter.EventoAdapter
import cr.ac.utn.practicaltest.databinding.LayoutListaEventosBinding

class EventoListaActivity : AppCompatActivity() {
    private lateinit var binding: LayoutListaEventosBinding
    private lateinit var controller: EventoController
    private lateinit var adapter: EventoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutListaEventosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        controller = EventoController(this)
        binding.recyclerEvents.layoutManager = LinearLayoutManager(this)
        binding.btnAdd.setOnClickListener {
            Util.openActivity(this, EventoFormularioActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        loadEvents()
    }

    private fun loadEvents() {
        val events = controller.getAllEvents()
        adapter = EventoAdapter(events) { event ->
            val intent = Intent(this, EventoFormularioActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        binding.recyclerEvents.adapter = adapter
    }
}