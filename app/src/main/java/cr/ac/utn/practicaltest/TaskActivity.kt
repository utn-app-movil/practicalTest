package cr.ac.utn.practicaltest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import Data.TaskMemoryManager

class TaskActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val etTitle = findViewById<EditText>(R.id.etTaskTitle)
        val etDescription = findViewById<EditText>(R.id.etTaskDescription)
        val etUser = findViewById<EditText>(R.id.etTaskUser)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        listView = findViewById(R.id.listViewTasks)

        actualizarLista()

        btnAddTask.setOnClickListener {

            TaskMemoryManager.addTask(
                etTitle.text.toString(),
                etDescription.text.toString(),
                etUser.text.toString()
            )

            actualizarLista()

            etTitle.text.clear()
            etDescription.text.clear()
            etUser.text.clear()
        }

        listView.setOnItemLongClickListener { _, _, position, _ ->

            val tarea = TaskMemoryManager.getAllTasks()[position]
            TaskMemoryManager.deleteTask(tarea.id)

            actualizarLista()
            true
        }
    }

    private fun actualizarLista() {
        val tareas = TaskMemoryManager.getAllTasks()
        val listaStrings = tareas.map { "${it.id}. ${it.title} - ${it.user}" }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaStrings)
        listView.adapter = adapter
    }
}
