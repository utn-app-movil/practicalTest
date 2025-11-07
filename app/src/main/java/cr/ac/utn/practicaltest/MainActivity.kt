package cr.ac.utn.practicaltest

import Controller.EventController
import Entity.Event
import Util.Util
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cr.ac.utn.practicaltest.adapter.EventAdapter
import cr.ac.utn.practicaltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var controller: EventController
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = EventController(this)
        setupRecyclerView()

        binding.btnAdd.setOnClickListener {
            Util.openActivity(this, EventActivity::class.java)
        }
    }

    override fun onResume() {
        super.onResume()
        loadEvents()
    }

    private fun setupRecyclerView() {
        binding.recyclerEvents.layoutManager = LinearLayoutManager(this)
    }

    private fun loadEvents() {
        val events = controller.getAllEvents()
        adapter = EventAdapter(events) { event ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        binding.recyclerEvents.adapter = adapter
    }
}