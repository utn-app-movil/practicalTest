package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import cr.ac.utn.practicaltest.Controller.EntertainmentController
import cr.ac.utn.practicaltest.Data.MemoryDataManager
import cr.ac.utn.practicaltest.Util.Result
import cr.ac.utn.practicaltest.Util.confirm
import cr.ac.utn.practicaltest.Util.toast
import cr.ac.utn.practicaltest.ui.SimpleAdapter
import cr.ac.utn.practicaltest.ui.SimpleRow

class EntertainmentActivity : AppCompatActivity() {

    private lateinit var controller: EntertainmentController
    private lateinit var spType: Spinner
    private lateinit var edtName: EditText
    private lateinit var edtDesc: EditText
    private lateinit var rv: RecyclerView
    private lateinit var adapter: SimpleAdapter

    // ID seleccionado (para actualizar/eliminar)
    private var selectedId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar como ActionBar para mostrar el menú CRUD
        findViewById<MaterialToolbar>(R.id.topAppBar)?.let { setSupportActionBar(it) }

        controller = EntertainmentController(MemoryDataManager)

        spType = findViewById(R.id.spType)
        edtName = findViewById(R.id.edtName)
        edtDesc = findViewById(R.id.edtDesc)
        rv = findViewById(R.id.rvList)

        adapter = SimpleAdapter { row ->
            selectedId = row.id
            edtName.setText(row.title)
            edtDesc.setText(row.subtitle)
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // Refrescar cuando cambia el tipo (Torneo/Entrenamiento/Equipo)
        spType.setSelection(0)
        spType.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                clearForm()
                refreshList()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) = Unit
        })

        // Restaurar estado si aplica
        selectedId = savedInstanceState?.getInt(STATE_SELECTED_ID)?.takeIf { it != -1 }

        refreshList()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SELECTED_ID, selectedId ?: -1)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.mnu_save -> { onSave(); true }
        R.id.mnu_delete -> { onDelete(); true }
        R.id.mnu_cancel -> { clearForm(); true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun onSave() {
        val name = edtName.text.toString().trim()
        val desc = edtDesc.text.toString().trim()
        if (name.isEmpty()) { toast(R.string.ErrorMsgAdd); return }

        val pos = spType.selectedItemPosition
        val result = when (pos) {
            0 -> controller.saveTournament(selectedId, name, desc)
            1 -> controller.saveTraining(selectedId, name, desc)
            else -> controller.saveTeam(selectedId, name, desc)
        }

        when (result) {
            is Result.Success -> {
                toast(R.string.MsgSaveSuccess)
                clearForm()
                refreshList()
            }
            is Result.Error -> toast(result.messageRes)
        }
    }

    private fun onDelete() {
        val id = selectedId ?: run { toast(R.string.MsgDataNoFound); return }
        val pos = spType.selectedItemPosition

        confirm(messageRes = R.string.TextDeleteActionQuestion) {
            val result = when (pos) {
                0 -> controller.deleteTournament(id)
                1 -> controller.deleteTraining(id)
                else -> controller.deleteTeam(id)
            }
            when (result) {
                is Result.Success -> {
                    toast(R.string.MsgDeleteSuccess)
                    clearForm()
                    refreshList()
                }
                is Result.Error -> toast(result.messageRes)
            }
        }
    }

    private fun clearForm() {
        selectedId = null
        edtName.text?.clear()
        edtDesc.text?.clear()
        edtName.requestFocus()
    }

    private fun refreshList() {
        val rows: List<SimpleRow> = when (spType.selectedItemPosition) {
            0 -> controller.listTournaments().map { SimpleRow(it.id, it.name, it.description) }
            1 -> controller.listTrainings().map { SimpleRow(it.id, it.name, it.description) }
            else -> controller.listTeams().map { SimpleRow(it.id, it.name, it.description) }
        }
        adapter.submit(rows)
    }

    companion object {
        private const val STATE_SELECTED_ID = "state_selected_id"
    }
}
