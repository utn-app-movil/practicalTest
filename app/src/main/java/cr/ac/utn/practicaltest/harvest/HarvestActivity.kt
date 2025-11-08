package cr.ac.utn.practicaltest.harvest

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HarvestActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etResponsible: EditText
    private lateinit var etSowDate: EditText
    private lateinit var etHarvestDate: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnBack: Button
    private lateinit var list: ListView

    private val controller = HarvestController(MemoryHarvestManager)
    private var selectedId: Int? = null
    private lateinit var adapter: ArrayAdapter<String>

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_harvest)

        etName = findViewById(R.id.etName)
        etResponsible = findViewById(R.id.etResponsible)
        etSowDate = findViewById(R.id.etSowDate)
        etHarvestDate = findViewById(R.id.etHarvestDate)
        btnAdd = findViewById(R.id.btnAdd)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnBack = findViewById(R.id.btnBack)
        list = findViewById(R.id.listHarvests)

        // DatePicker al tocar los campos de fecha
        etSowDate.setOnClickListener { showDatePicker(etSowDate) }
        etHarvestDate.setOnClickListener { showDatePicker(etHarvestDate) }

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, renderList())
        list.adapter = adapter

        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val resp = etResponsible.text.toString().trim()
            val sow = etSowDate.text.toString().trim()
            val harv = etHarvestDate.text.toString().trim()
            if (name.isEmpty() || resp.isEmpty() || sow.isEmpty() || harv.isEmpty()) {
                toast(getString(R.string.msg_fill_fields)); return@setOnClickListener
            }
            controller.add(name, resp, sow, harv)
            clearSelection(); refresh()
            toast(getString(R.string.msg_saved))
        }

        list.setOnItemClickListener { _, _, position, _ ->
            val item = controller.all()[position]
            selectedId = item.id
            etName.setText(item.name)
            etResponsible.setText(item.responsible)
            etSowDate.setText(item.sowDate)
            etHarvestDate.setText(item.harvestDate)
        }

        btnUpdate.setOnClickListener {
            val id = selectedId ?: return@setOnClickListener toast(getString(R.string.msg_select_item))
            val name = etName.text.toString().trim()
            val resp = etResponsible.text.toString().trim()
            val sow = etSowDate.text.toString().trim()
            val harv = etHarvestDate.text.toString().trim()
            if (name.isEmpty() || resp.isEmpty() || sow.isEmpty() || harv.isEmpty()) {
                toast(getString(R.string.msg_fill_fields)); return@setOnClickListener
            }
            controller.update(id, name, resp, sow, harv)
            clearSelection(); refresh()
            toast(getString(R.string.msg_updated))
        }

        btnDelete.setOnClickListener {
            val id = selectedId ?: return@setOnClickListener toast(getString(R.string.msg_select_item))
            android.app.AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_confirm_delete))
                .setPositiveButton(R.string.delete) { _, _ ->
                    controller.delete(id)
                    clearSelection(); refresh()
                    toast(getString(R.string.msg_deleted))
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun showDatePicker(target: EditText) {
        val cal = Calendar.getInstance()

        // Si ya hay fecha escrita, abrir el diálogo en esa fecha
        val existing = target.text?.toString()?.trim().orEmpty()
        if (existing.isNotEmpty()) {
            try {
                dateFormat.parse(existing)?.let { cal.time = it }
            } catch (_: Exception) { /* ignorar parse fallido */ }
        }

        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            cal.set(year, month, dayOfMonth)
            target.setText(dateFormat.format(cal.time))
        }, y, m, d).show()
    }

    private fun refresh() {
        adapter.clear()
        adapter.addAll(renderList())
        adapter.notifyDataSetChanged()
    }

    private fun renderList(): List<String> =
        controller.all().map { i ->
            "${i.name} • ${i.responsible} • " +
                    "${getString(R.string.lbl_sow)}: ${i.sowDate} • " +
                    "${getString(R.string.lbl_harvest)}: ${i.harvestDate}"
        }

    private fun clearSelection() {
        selectedId = null
        etName.text.clear()
        etResponsible.text.clear()
        etSowDate.text.clear()
        etHarvestDate.text.clear()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
