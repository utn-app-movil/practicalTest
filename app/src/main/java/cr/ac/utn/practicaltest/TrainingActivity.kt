package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import Controller.TrainingController
import Entity.Training

class TrainingActivity : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtName: EditText
    private lateinit var edtDescription: EditText
    private lateinit var edtSchedule: EditText
    private lateinit var edtCapacity: EditText
    private lateinit var edtInstructor: EditText
    private lateinit var lstTrainings: ListView

    private lateinit var trainingController: TrainingController
    private lateinit var trainings: MutableList<Training>
    private lateinit var adapter: ArrayAdapter<Training>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_training)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(v.paddingLeft, systemBars.top, v.paddingRight, v.paddingBottom)
            insets
        }

        edtId = findViewById(R.id.edtId_training)
        edtName = findViewById(R.id.edtName_training)
        edtDescription = findViewById(R.id.edtDescription_training)
        edtSchedule = findViewById(R.id.edtSchedule_training)
        edtCapacity = findViewById(R.id.edtCapacity_training)
        edtInstructor = findViewById(R.id.edtInstructor_training)
        lstTrainings = findViewById(R.id.lstTrainings)

        trainingController = TrainingController()
        trainings = trainingController.getAllTrainings().toMutableList()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, trainings)
        lstTrainings.adapter = adapter

        lstTrainings.setOnItemClickListener { _, _, position, _ ->
            val selectedTraining = trainings[position]
            edtId.setText(selectedTraining.id_training)
            edtName.setText(selectedTraining.name)
            edtDescription.setText(selectedTraining.description)
            edtSchedule.setText(selectedTraining.schedule)
            edtCapacity.setText(selectedTraining.capacity.toString())
            edtInstructor.setText(selectedTraining.instructor)
            edtId.isEnabled = false // Disable ID field for updates
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnu_save -> saveTraining()
            R.id.mnu_delete -> deleteTraining()
            R.id.mnu_cancel -> clearForm()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveTraining() {
        if (edtId.isEnabled) {
            addTraining()
        } else {
            updateTraining()
        }
    }

    private fun addTraining() {
        try {
            val id = edtId.text.toString()
            val name = edtName.text.toString()
            val description = edtDescription.text.toString()
            val schedule = edtSchedule.text.toString()
            val capacity = edtCapacity.text.toString().toInt()
            val instructor = edtInstructor.text.toString()

            if (id.isBlank() || name.isBlank()) {
                Toast.makeText(this, "ID and Name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }

            if (trainingController.getTraining(id) != null) {
                Toast.makeText(this, "Training with this ID already exists", Toast.LENGTH_SHORT).show()
                return
            }

            val training = Training(id, name, description, schedule, capacity, instructor)
            trainingController.addTraining(training)

            refreshList()
            clearForm()
            Toast.makeText(this, "Training added", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Capacity must be a number", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateTraining() {
        try {
            val id = edtId.text.toString()
            val name = edtName.text.toString()
            val description = edtDescription.text.toString()
            val schedule = edtSchedule.text.toString()
            val capacity = edtCapacity.text.toString().toInt()
            val instructor = edtInstructor.text.toString()

            if (name.isBlank()) {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }
            
            val training = Training(id, name, description, schedule, capacity, instructor)
            trainingController.updateTraining(training)

            refreshList()
            clearForm()
            Toast.makeText(this, "Training updated", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Capacity must be a number", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteTraining() {
        try {
            val id = edtId.text.toString()

            if (id.isBlank() || trainingController.getTraining(id) == null) {
                Toast.makeText(this, "Select a training to delete", Toast.LENGTH_SHORT).show()
                return
            }

            trainingController.deleteTraining(id)

            refreshList()
            clearForm()
            Toast.makeText(this, "Training deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshList() {
        trainings.clear()
        trainings.addAll(trainingController.getAllTrainings())
        adapter.notifyDataSetChanged()
    }

    private fun clearForm() {
        edtId.setText("")
        edtName.setText("")
        edtDescription.setText("")
        edtSchedule.setText("")
        edtCapacity.setText("")
        edtInstructor.setText("")
        edtId.isEnabled = true
    }
}