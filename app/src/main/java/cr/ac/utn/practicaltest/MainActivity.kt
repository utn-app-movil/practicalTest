package cr.ac.utn.practicaltest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.NewsListActivity
import cr.ac.utn.practicaltest.databinding.ActivityMainBinding
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//CRUD de facturas generadas por servicios.
//Gestión de Facturas
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnViewNews.setOnClickListener {
            startActivity(Intent(this, NewsListActivity::class.java))
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
    }
}