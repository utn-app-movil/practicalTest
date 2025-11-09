package cr.ac.utn.practicaltest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewNews.setOnClickListener {
            startActivity(Intent(this, NewsListActivity::class.java))
        }

        val btnUserActivity = findViewById<Button>(R.id.btnManage_user)
        btnUserActivity.setOnClickListener (View.OnClickListener { view ->
            Util.Util.openActivity(this, UserActivity::class.java)
        })

        val btnEvent = findViewById<Button>(R.id.btnEvent_main)
        btnEvent.setOnClickListener(View.OnClickListener{ view->

        })

        val btnTask = findViewById<Button>(R.id.btnTask_main)
        btnTask.setOnClickListener(View.OnClickListener{ view->

        })

        val btnProduct = findViewById<Button>(R.id.btnProduct_main)
        btnProduct.setOnClickListener(View.OnClickListener{ view->

        })

        val btnBooking = findViewById<Button>(R.id.btnBooking_main)
        btnBooking.setOnClickListener(View.OnClickListener{ view->

        })

        val btnSurvey = findViewById<Button>(R.id.btnSurvery_main)
        btnSurvey.setOnClickListener(View.OnClickListener{ view->

        })

        val btnReceipt = findViewById<Button>(R.id.btnReceipt_main)
        btnReceipt.setOnClickListener(View.OnClickListener{ view->

        })

        val btnService = findViewById<Button>(R.id.btnService_main)
        btnService.setOnClickListener(View.OnClickListener{ view->

        })

        val btnSupplier = findViewById<Button>(R.id.btnSupplier_main)
        btnSupplier.setOnClickListener(View.OnClickListener{ view->

        })

        val btnIncident = findViewById<Button>(R.id.btnIncident_main)
        btnIncident.setOnClickListener(View.OnClickListener{ view->

        })

        val btnVehicle = findViewById<Button>(R.id.btnVehicle_main)
        btnVehicle.setOnClickListener(View.OnClickListener{ view->

        })

        val btnVisit = findViewById<Button>(R.id.btnVisit_main)
        btnVisit.setOnClickListener(View.OnClickListener{ view->

        })

        val btnMeeting = findViewById<Button>(R.id.btnMeeting_main)
        btnMeeting.setOnClickListener(View.OnClickListener{ view->

        })

        val btnTraining = findViewById<Button>(R.id.btnTraining_main)
        btnTraining.setOnClickListener(View.OnClickListener{ view->

        })

        val btnInventory = findViewById<Button>(R.id.btnInventory_main)
        btnInventory.setOnClickListener(View.OnClickListener{ view->

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
        btnLearning.setOnClickListener(View.OnClickListener{ view->

        })

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
        btnRecipe.setOnClickListener(View.OnClickListener{ view->

        })
    }
}
