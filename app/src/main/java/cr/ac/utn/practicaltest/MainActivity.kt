package cr.ac.utn.practicaltest

import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnViewNews = findViewById<Button>(R.id.btnViewNews)
        btnViewNews.setOnClickListener {
            val intent = Intent(this, NewsListActivity::class.java)
            startActivity(intent)
        }
        val btnManageParticipant = findViewById<Button>(R.id.btnManage_participant)
        btnManageParticipant.setOnClickListener {
            Util.Util.openActivity(this, ParticipantActivity::class.java)
        }
        val btnManageScheduleActivity = findViewById<Button>(R.id.btnManage_schedule)
        btnManageScheduleActivity.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
        val btnManageVolunteering = findViewById<Button>(R.id.btnManage_volunteering)
        btnManageVolunteering.setOnClickListener {
            Util.Util.openActivity(this, VolunteerActivity::class.java)
        }

    }
}