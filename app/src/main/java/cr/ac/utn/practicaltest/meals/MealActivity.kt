package cr.ac.utn.practicaltest.meals

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R
import java.util.Calendar

class MealActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etQuantity: EditText
    private lateinit var etDeliveryDate: EditText
    private lateinit var etNotes: EditText
    private lateinit var btnAddUpdate: Button
    private lateinit var btnClear: Button
    private lateinit var btnBackMeals: Button
    private lateinit var listMeals: ListView

    private val manager = MemoryMealManager()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedMealId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        etName = findViewById(R.id.etMealName)
        etQuantity = findViewById(R.id.etMealQuantity)
        etDeliveryDate = findViewById(R.id.etDeliveryDate)
        etNotes = findViewById(R.id.etMealNotes)
        btnAddUpdate = findViewById(R.id.btnAddUpdateMeal)
        btnClear = findViewById(R.id.btnClearMeal)
        btnBackMeals = findViewById(R.id.btnBackMeals)
        listMeals = findViewById(R.id.listMeals)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buildRows())
        listMeals.adapter = adapter

        etDeliveryDate.setOnClickListener { showDatePicker() }

        btnAddUpdate.setOnClickListener {
            val name = etName.text.toString().trim()
            val qty = etQuantity.text.toString().trim().toIntOrNull()
            val date = etDeliveryDate.text.toString().trim()
            val notes = etNotes.text.toString().trim().ifEmpty { null }

            if (name.isEmpty() || qty == null || qty < 1 || date.isEmpty()) {
                Toast.makeText(this, getString(R.string.msg_fill_fields), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = selectedMealId
            if (id == null) {
                manager.addMeal(MealEntity(0, name, qty, date, notes))
                Toast.makeText(this, getString(R.string.meal_added), Toast.LENGTH_SHORT).show()
            } else {
                manager.updateMeal(MealEntity(id, name, qty, date, notes))
                Toast.makeText(this, getString(R.string.meal_updated), Toast.LENGTH_SHORT).show()
            }
            resetForm(); refreshList()
        }

        btnClear.setOnClickListener { resetForm() }

        listMeals.setOnItemClickListener { _, _, pos, _ ->
            val m = manager.getAllMeals()[pos]
            selectedMealId = m.id
            etName.setText(m.name)
            etQuantity.setText(m.quantity.toString())
            etDeliveryDate.setText(m.deliveryDate)
            etNotes.setText(m.notes ?: "")
            btnAddUpdate.text = getString(R.string.update)
        }

        listMeals.setOnItemLongClickListener { _, _, pos, _ ->
            val m = manager.getAllMeals()[pos]
            AlertDialog.Builder(this)
                .setTitle(R.string.delete)
                .setMessage(getString(R.string.meal_delete_confirm, m.name))
                .setPositiveButton(R.string.delete) { _, _ ->
                    manager.deleteMeal(m.id)
                    if (selectedMealId == m.id) resetForm()
                    refreshList()
                    Toast.makeText(this, getString(R.string.meal_deleted), Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
            true
        }

        btnBackMeals.setOnClickListener { finish() }
    }

    private fun buildRows() = manager.getAllMeals().map { m ->
        val note = if (m.notes.isNullOrBlank()) "" else " • ${m.notes}"
        "🍲 ${m.name} — ${m.quantity} ${getString(R.string.portions)} — ${getString(R.string.delivery)} ${m.deliveryDate}$note"
    }.toMutableList()

    private fun refreshList() { adapter.clear(); adapter.addAll(buildRows()); adapter.notifyDataSetChanged() }

    private fun resetForm() {
        selectedMealId = null
        etName.text?.clear(); etQuantity.text?.clear(); etDeliveryDate.text?.clear(); etNotes.text?.clear()
        btnAddUpdate.text = getString(R.string.add)
    }

    private fun showDatePicker() {
        val c = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, y, m, d -> etDeliveryDate.setText("%02d/%02d/%d".format(d, m + 1, y)) },
            c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
