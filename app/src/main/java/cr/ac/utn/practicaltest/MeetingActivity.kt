package cr.ac.utn.practicaltest

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MeetingActivity : AppCompatActivity() {

    private lateinit var meetingList: MutableList<Meeting>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MeetingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meeting)

        meetingList = mutableListOf()
        recyclerView = findViewById(R.id.recyclerMeetings)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MeetingAdapter(meetingList,
            onEdit = { meeting, position -> showEditMeetingDialog(meeting, position) },
            onDelete = { position -> deleteMeeting(position) }
        )
        recyclerView.adapter = adapter

        val btnAddMeeting = findViewById<Button>(R.id.btnAddMeeting)
        btnAddMeeting.setOnClickListener {
            showAddMeetingDialog()
        }
    }

    private fun showAddMeetingDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_meeting, null)
        val dateInput = dialogView.findViewById<EditText>(R.id.inputDate)
        val timeInput = dialogView.findViewById<EditText>(R.id.inputTime)
        val participantsInput = dialogView.findViewById<EditText>(R.id.inputParticipants)

        AlertDialog.Builder(this)
            .setTitle("Add Meeting")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val date = dateInput.text.toString()
                val time = timeInput.text.toString()
                val participants = participantsInput.text.toString()

                if (date.isNotEmpty() && time.isNotEmpty() && participants.isNotEmpty()) {
                    val meeting = Meeting(date, time, participants)
                    meetingList.add(meeting)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditMeetingDialog(meeting: Meeting, position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_meeting, null)
        val dateInput = dialogView.findViewById<EditText>(R.id.inputDate)
        val timeInput = dialogView.findViewById<EditText>(R.id.inputTime)
        val participantsInput = dialogView.findViewById<EditText>(R.id.inputParticipants)

        dateInput.setText(meeting.date)
        timeInput.setText(meeting.time)
        participantsInput.setText(meeting.participants)

        AlertDialog.Builder(this)
            .setTitle("Edit Meeting")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val updatedDate = dateInput.text.toString()
                val updatedTime = timeInput.text.toString()
                val updatedParticipants = participantsInput.text.toString()

                if (updatedDate.isNotEmpty() && updatedTime.isNotEmpty() && updatedParticipants.isNotEmpty()) {
                    meetingList[position] = Meeting(updatedDate, updatedTime, updatedParticipants)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteMeeting(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete Meeting")
            .setMessage("Are you sure you want to delete this meeting?")
            .setPositiveButton("Yes") { _, _ ->
                meetingList.removeAt(position)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("No", null)
            .show()
    }
}

//Model class
data class Meeting(
    val date: String,
    val time: String,
    val participants: String
)

//Adapter class
class MeetingAdapter(
    private val meetings: MutableList<Meeting>,
    private val onEdit: (Meeting, Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<MeetingAdapter.MeetingViewHolder>() {

    class MeetingViewHolder(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtTime: TextView = view.findViewById(R.id.txtTime)
        val txtParticipants: TextView = view.findViewById(R.id.txtParticipants)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): MeetingViewHolder {
        val view = android.view.LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meeting, parent, false)
        return MeetingViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeetingViewHolder, position: Int) {
        val meeting = meetings[position]
        holder.txtDate.text = "Date: ${meeting.date}"
        holder.txtTime.text = "Time: ${meeting.time}"
        holder.txtParticipants.text = "Participants: ${meeting.participants}"

        holder.btnEdit.setOnClickListener { onEdit(meeting, position) }
        holder.btnDelete.setOnClickListener { onDelete(position) }
    }

    override fun getItemCount(): Int = meetings.size
}
