package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import cr.ac.utn.practicaltest.Controller.EntertainmentController
import cr.ac.utn.practicaltest.Data.EntertainmentMemoryRepository
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

    private var selectedId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entertainment)

        // Toolbar como ActionBar
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)

        controller = EntertainmentController(EntertainmentMemoryRepository)

        spType  = findViewById<Spinner>(R.id.spType)
        edtName = findViewById<EditText>(R.id.edtName)
        edtDesc = findViewById<EditText>(R.id.edtDesc)
        rv      = findViewById<RecyclerView>(R.id.rvList)

        adapter = SimpleAdapter { row ->
            selectedId = row.id
            edtName.setText(row.title)
            edtDesc.setText(row.subtitle)
        }
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        spType.setSelection(0)
        spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                clearForm()
                refreshList()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }

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

        val res = when (spType.selectedItemPosition) {
            0 -> controller.saveTournament(selectedId, name, desc)
            1 -> controller.saveTraining(selectedId, name, desc)
            else -> controller.saveTeam(selectedId, name, desc)
        }
        when (res) {
            is Result.Success -> { toast(R.string.MsgSaveSuccess); clearForm(); refreshList() }
            is Result.Error   -> toast(res.messageRes)
        }
    }

    private fun onDelete() {
        val id = selectedId ?: return toast(R.string.MsgDataNoFound)
        confirm(messageRes = R.string.TextDeleteActionQuestion) {
            val res = when (spType.selectedItemPosition) {
                0 -> controller.deleteTournament(id)
                1 -> controller.deleteTraining(id)
                else -> controller.deleteTeam(id)
            }
            when (res) {
                is Result.Success -> { toast(R.string.MsgDeleteSuccess); clearForm(); refreshList() }
                is Result.Error   -> toast(res.messageRes)
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

    companion object { private const val STATE_SELECTED_ID = "state_selected_id" }
}
