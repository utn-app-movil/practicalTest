package Controller.Incident

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.R
import Controller.Incident.SecurityServActivity
import Controller.Incident.WaterServActivity

class IncidentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incident)

        findViewById<ImageButton>(R.id.btnElecServ).setOnClickListener {
            startActivity(Intent(this, ElecServActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnWaterServ).setOnClickListener {
            startActivity(Intent(this, WaterServActivity::class.java))
        }
        findViewById<ImageButton>(R.id.btnSecServ).setOnClickListener {
            startActivity(Intent(this, SecurityServActivity::class.java))
        }
    }
}