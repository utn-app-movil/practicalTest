package cr.ac.utn.practicaltest


import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val btnUserActivity = findViewById<Button>(R.id.btnManage_user)
        btnUserActivity.setOnClickListener (View.OnClickListener { view ->
            Util.Util.openActivity(this, UserActivity::class.java)
        })
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