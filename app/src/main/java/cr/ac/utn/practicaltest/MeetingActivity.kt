package cr.ac.utn.practicaltest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MeetingActivity : AppCompatActivity() {

    private lateinit var etDate: EditText
    private lateinit var etTime: EditText
    private lateinit var etParticipants: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    private lateinit var meetingList: ListView

    private var meetings = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedPosition = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting)

        etDate = findViewById(R.id.etDate)
        etTime = findViewById(R.id.etTime)
        etParticipants = findViewById(R.id.etParticipants)
        btnAdd = findViewById(R.id.btnSave)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)
        meetingList = findViewById(R.id.meetingList)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, meetings)
        meetingList.adapter = adapter

        btnAdd.setOnClickListener {
            val text = "Date: ${etDate.text}, Time: ${etTime.text}, Participants: ${etParticipants.text}"
            meetings.add(text)
            adapter.notifyDataSetChanged()
            clearFields()
        }

        meetingList.setOnItemClickListener { _, _, position, _ ->
            selectedPosition = position
            val data = meetings[position].split(", ")
            etDate.setText(data[0].replace("Date: ", ""))
            etTime.setText(data[1].replace("Time: ", ""))
            etParticipants.setText(data[2].replace("Participants: ", ""))
        }

        btnEdit.setOnClickListener {
            if (selectedPosition != -1) {
                val text = "Date: ${etDate.text}, Time: ${etTime.text}, Participants: ${etParticipants.text}"
                meetings[selectedPosition] = text
                adapter.notifyDataSetChanged()
                clearFields()
                selectedPosition = -1
            }
        }

        btnDelete.setOnClickListener {
            if (selectedPosition != -1) {
                meetings.removeAt(selectedPosition)
                adapter.notifyDataSetChanged()
                clearFields()
                selectedPosition = -1
            }
        }
    }

    private fun clearFields() {
        etDate.setText("")
        etTime.setText("")
        etParticipants.setText("")
    }
}
