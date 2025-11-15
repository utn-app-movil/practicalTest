package Controller

import Data.VisitDataManager
import Entity.Visit
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R

class VisitActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: VisitAdapter
    private lateinit var btnAddVisit: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerViewVisits)
        btnAddVisit = findViewById(R.id.btnAddVisit)
        btnBack = findViewById(R.id.btnBackFromVisits)

        // Configurar RecyclerView
        setupRecyclerView()

        // Botón para agregar nueva visita
        btnAddVisit.setOnClickListener {
            val intent = Intent(this, AddEditVisitActivity::class.java)
            startActivity(intent)
        }

        // Botón para regresar
        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadVisits()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VisitAdapter(
            visits = mutableListOf(),
            onEditClick = { visit -> editVisit(visit) },
            onDeleteClick = { visit -> confirmDeleteVisit(visit) }
        )
        recyclerView.adapter = adapter
    }

    private fun loadVisits() {
        val visits = VisitDataManager.getAllVisits()
        adapter.updateVisits(visits)
    }

    private fun editVisit(visit: Visit) {
        val intent = Intent(this, AddEditVisitActivity::class.java)
        intent.putExtra("VISIT_ID", visit.id)
        startActivity(intent)
    }

    private fun confirmDeleteVisit(visit: Visit) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Visita")
            .setMessage("¿Está seguro que desea eliminar la visita de ${visit.visitorName}?")
            .setPositiveButton("Eliminar") { _, _ ->
                deleteVisit(visit)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteVisit(visit: Visit) {
        if (VisitDataManager.deleteVisit(visit.id)) {
            Toast.makeText(this, "Visita eliminada", Toast.LENGTH_SHORT).show()
            loadVisits()
        } else {
            Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show()
        }
    }
}