package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import Controller.LearningController
import Data.LearningMemoryRepository
import Entity.LearningClass

class LearningActivity : AppCompatActivity() {

    // UI
    private lateinit var etTitle: EditText
    private lateinit var etSchedule: EditText
    private lateinit var etProfessor: EditText
    private lateinit var listView: ListView
    private lateinit var emptyView: TextView

    // Estado
    private val controller = LearningController(LearningMemoryRepository())
    private val items = mutableListOf<LearningClass>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedIndex: Int = -1   // -1 = ninguno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_learning)

        supportActionBar?.title = getString(R.string.learning_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Referencias UI
        etTitle = findViewById(R.id.etTitle)
        etSchedule = findViewById(R.id.etSchedule)
        etProfessor = findViewById(R.id.etProfessor)
        listView = findViewById(R.id.lvClasses)
        emptyView = findViewById(R.id.tvEmpty)

        // Datos iniciales
        items.clear()
        items.addAll(controller.list())
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, items.map { it.toString() }.toMutableList())
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        updateEmptyState()

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
            listView.setItemChecked(position, true)
            val item = items[position]
            etTitle.setText(item.title)
            etSchedule.setText(item.schedule)
            etProfessor.setText(item.professor)
        }
        listView.setOnItemLongClickListener { _, _, position, _ ->
            confirmDelete(position)
            true
        }
    }

    // Menú del profe
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.mnu_save     -> { onSave(); true }
            R.id.mnu_delete   -> { onDelete(); true }
            R.id.mnu_cancel   -> { onCancel(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Guardar (add o update según selección)
    private fun onSave() {
        val title = etTitle.text.toString()
        val schedule = etSchedule.text.toString()
        val professor = etProfessor.text.toString()

        if (selectedIndex >= 0) {
            val id = items[selectedIndex].id
            val result = controller.update(id, title, schedule, professor)
            result.onSuccess { ok ->
                if (ok) {
                    items[selectedIndex] = items[selectedIndex].copy(title = title.trim(), schedule = schedule.trim(), professor = professor.trim())
                    refreshAdapter()
                    toast(getString(R.string.MsgSaveSuccess))
                } else {
                    toast(getString(R.string.ErrorMsgUpdate))
                }
            }.onFailure { ex ->
                showFailure(ex)
            }
        } else {
            val result = controller.create(title, schedule, professor)
            result.onSuccess { new ->
                items.add(new)
                refreshAdapter()
                toast(getString(R.string.MsgSaveSuccess))
            }.onFailure { ex ->
                showFailure(ex)
            }
        }
        updateEmptyState()
    }

    private fun onDelete() {
        if (selectedIndex < 0) {
            toast(getString(R.string.MsgDataNoFound))
            return
        }
        confirmDelete(selectedIndex)
    }

    private fun onCancel() {
        clearForm()
        listView.clearChoices()
        adapter.notifyDataSetChanged()
        selectedIndex = -1
    }

    private fun confirmDelete(position: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.TextTitleDialogQuestion))
            .setMessage(getString(R.string.TextDeleteActionQuestion))
            .setPositiveButton(getString(R.string.TextYes)) { _, _ ->
                val id = items[position].id
                if (controller.delete(id)) {
                    items.removeAt(position)
                    refreshAdapter()
                    toast(getString(R.string.MsgDeleteSuccess))
                    selectedIndex = -1
                    listView.clearChoices()
                } else {
                    toast(getString(R.string.ErrorMsgRemove))
                }
                updateEmptyState()
            }
            .setNegativeButton(getString(R.string.TextNo), null)
            .show()
    }

    private fun refreshAdapter() {
        adapter.clear()
        adapter.addAll(items.map { it.toString() })
        adapter.notifyDataSetChanged()
        clearForm()
    }

    private fun clearForm() {
        etTitle.text?.clear()
        etSchedule.text?.clear()
        etProfessor.text?.clear()
    }

    private fun updateEmptyState() {
        emptyView.visibility = if (items.isEmpty()) TextView.VISIBLE else TextView.GONE
        listView.visibility = if (items.isEmpty()) ListView.GONE else ListView.VISIBLE
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    private fun showFailure(ex: Throwable) {
        when (ex) {
            is IllegalArgumentException -> {
                toast(getString(R.string.ErrorMsgAdd))
            }
            is IllegalStateException -> {
                toast(getString(R.string.MsgDuplicateDate))
            }
            else -> toast(getString(R.string.ErrorMsgAdd))
        }
    }
}
