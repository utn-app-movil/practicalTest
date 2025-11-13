package cr.ac.utn.practicaltest

import Controller.SpaceController
import Entity.Space
import Entity.SpaceType
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SpaceListActivity : AppCompatActivity() {

    private lateinit var rvSpaces: RecyclerView
    private lateinit var btnAddSpace: FloatingActionButton
    private lateinit var spinnerSpaceType: Spinner
    private lateinit var spaceController: SpaceController
    private lateinit var spaceAdapter: SpaceAdapter
    private var allSpaces: List<Space> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_list)

        // Initialize controller
        spaceController = SpaceController(this)

        // Initialize views
        rvSpaces = findViewById(R.id.rvSpaces)
        btnAddSpace = findViewById(R.id.btnAddSpace)
        spinnerSpaceType = findViewById(R.id.spinnerSpaceType)

        // Setup RecyclerView
        rvSpaces.layoutManager = LinearLayoutManager(this)

        // Setup spinner with space types
        setupSpinner()

        // Load spaces
        loadSpaces()

        // Setup FAB click
        btnAddSpace.setOnClickListener {
            val intent = Intent(this, SpaceFormActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadSpaces()
    }

    private fun setupSpinner() {
        val spaceTypes = mutableListOf("All Types")
        spaceTypes.addAll(SpaceType.values().map { it.name })

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spaceTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSpaceType.adapter = adapter

        spinnerSpaceType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filterSpaces(if (position == 0) null else SpaceType.values()[position - 1])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                filterSpaces(null)
            }
        }
    }

    private fun loadSpaces() {
        allSpaces = spaceController.getAllSpaces()
        filterSpaces(null)
    }

    private fun filterSpaces(type: SpaceType?) {
        val filteredSpaces = if (type == null) {
            allSpaces
        } else {
            allSpaces.filter { it.Type == type }
        }

        spaceAdapter = SpaceAdapter(
            filteredSpaces,
            onItemClick = { space ->
                val intent = Intent(this, SpaceFormActivity::class.java)
                intent.putExtra("SPACE_ID", space.ID)
                startActivity(intent)
            },
            onSpaceDeleted = {
                loadSpaces()
            }
        )
        rvSpaces.adapter = spaceAdapter
    }
}
