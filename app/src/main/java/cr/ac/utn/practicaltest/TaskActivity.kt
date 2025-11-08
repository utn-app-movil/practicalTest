package cr.ac.utn.practicaltest
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tuapp.data.DataManager
import com.tuapp.entity.Task
import com.tuapp.util.Utils
import com.tuapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var etTaskName: EditText
    private lateinit var etTaskDescription: EditText
    private lateinit var btnAddTask: Button
    private lateinit var btnUpdateTask: Button
    private lateinit var btnDeleteTask: Button
    private lateinit var lvTasks: ListView

    private val dataManager = DataManager()
    private var selectedTaskId: Int? = null
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTaskName = findViewById(R.id.etTaskName)
        etTaskDescription = findViewById(R.id.etTaskDescription)
        btnAddTask = findViewById(R.id.btnAddTask)
        btnUpdateTask = findViewById(R.id.btnUpdateTask)
        btnDeleteTask = findViewById(R.id.btnDeleteTask)
        lvTasks = findViewById(R.id.lvTasks)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        lvTasks.adapter = adapter

        // Botón Agregar
        btnAddTask.setOnClickListener {
            val name = etTaskName.text.toString()
            val desc = etTaskDescription.text.toString()
            if (name.isNotEmpty() && desc.isNotEmpty()) {
                val task = Task(Utils.generateId(), name, desc)
                dataManager.addTask(task)
                refreshList()
                clearFields()
            } else {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Seleccionar tarea
        lvTasks.setOnItemClickListener { _, _, position, _ ->
            val task = dataManager.getAllTasks()[position]
            etTaskName.setText(task.getName())
            etTaskDescription.setText(task.getDescription())
            selectedTaskId = task.getId()
        }

        // Botón Actualizar
        btnUpdateTask.setOnClickListener {
            val id = selectedTaskId
            if (id != null) {
                dataManager.updateTask(id, etTaskName.text.toString(), etTaskDescription.text.toString())
                refreshList()
                clearFields()
            } else {
                Toast.makeText(this, "Selecciona una tarea para actualizar", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Eliminar
        btnDeleteTask.setOnClickListener {
            val id = selectedTaskId
            if (id != null) {
                dataManager.deleteTask(id)
                refreshList()
                clearFields()
            } else {
                Toast.makeText(this, "Selecciona una tarea para eliminar", Toast.LENGTH_SHORT).show()
            }
        }

        refreshList()
    }

    private fun refreshList() {
        val tasks = dataManager.getAllTasks()
        adapter.clear()
        adapter.addAll(tasks.map { "${it.getId()} - ${it.getName()}" })
    }

    private fun clearFields() {
        etTaskName.text.clear()
        etTaskDescription.text.clear()
        selectedTaskId = null
    }
}

