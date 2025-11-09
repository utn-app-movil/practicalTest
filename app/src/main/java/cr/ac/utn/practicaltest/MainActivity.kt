package cr.ac.utn.practicaltest

import Util.Util
import Controller.Incident.IncidentActivity
import android.content.Intent
import android.os.Bundle
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

        val btnUser = findViewById<Button>(R.id.btnUser_main)
        btnUser.setOnClickListener(View.OnClickListener{ view ->
            Util.openActivity(this, UserActivity::class.java)

        })

        val btnEvent = findViewById<Button>(R.id.btnEvent_main)
        btnEvent.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            startActivity(intent)
        }

        val btnNews = findViewById<Button>(R.id.btnNews_main)
        btnNews.setOnClickListener(View.OnClickListener{ view->

        })

        val btnTask = findViewById<Button>(R.id.btnTask_main)
        btnTask.setOnClickListener(View.OnClickListener{ view->

        })

        val btnProduct = findViewById<Button>(R.id.btnProduct_main)
        btnProduct.setOnClickListener(View.OnClickListener{ view->

        })

        val btnBooking = findViewById<Button>(R.id.btnBooking_main)
        btnBooking.setOnClickListener(View.OnClickListener{ view->
            Util.openActivity(this,
                BookingActivity::class.java)
        })

        val btnSurvey = findViewById<Button>(R.id.btnSurvey_main)
        btnSurvey.setOnClickListener {
            Util.openActivity(this, SurveyActivity::class.java)
        }

        val btnReceipt = findViewById<Button>(R.id.btnReceipt_main)
        btnReceipt.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnService = findViewById<Button>(R.id.btnService_main)
        btnService.setOnClickListener(View.OnClickListener { view ->

            Util.openActivity(this, ServiceActivity::class.java)
        })

        val btnSupplier = findViewById<Button>(R.id.btnSupplier_main)
        btnSupplier.setOnClickListener(View.OnClickListener{ view->
            Util.openActivity(this, SupplierActivity::class.java)
        })

        val btnIncident = findViewById<Button>(R.id.btnIncident_main)
        btnIncident.setOnClickListener {
            Util.openActivity(this, IncidentActivity::class.java)
        }

        val btnVehicle = findViewById<Button>(R.id.btnVehicle_main)
        btnVehicle.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnVisit = findViewById<Button>(R.id.btnVisit_main)
        btnVisit.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnMeeting = findViewById<Button>(R.id.btnMeeting_main)
        btnMeeting.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(this, MeetingActivity::class.java)
            startActivity(intent)
        })

        val btnTraining = findViewById<Button>(R.id.btnTraining_main)
        btnTraining.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnInventory = findViewById<Button>(R.id.btnInventory_main)
        btnInventory.setOnClickListener(View.OnClickListener{ view->
            Util.openActivity(this, InventoryActivity:: class.java)
        })

        val btnVolunteer = findViewById<Button>(R.id.btnVolunteer_main)
        btnVolunteer.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnEntertainment = findViewById<Button>(R.id.btnEntertainment_main)
        btnEntertainment.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnLibrary = findViewById<Button>(R.id.btnLibrary_main)
        btnLibrary.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnRent = findViewById<Button>(R.id.btnRent_main)
        btnRent.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnLearning = findViewById<Button>(R.id.btnLearning_main)
        btnLearning.setOnClickListener {
            startActivity(Intent(this, LearningActivity::class.java))

        }
        val btnHarvest = findViewById<Button>(R.id.btnHarvest_main)
        btnHarvest.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnMeal = findViewById<Button>(R.id.btnMeal_main)
        btnMeal.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnSponsor = findViewById<Button>(R.id.btnSponsor_main)
        btnSponsor.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnFilm = findViewById<Button>(R.id.btnFilm_main)
        btnFilm.setOnClickListener(View.OnClickListener{ view->
            
        })

        val btnRecipe = findViewById<Button>(R.id.btnRecipe_main)
        btnRecipe.setOnClickListener {
            val intent = Intent(this, RecipeActivity::class.java)
            startActivity(intent)
        }
        btnRecipe.setOnClickListener(View.OnClickListener{ view->

        })
    }
}