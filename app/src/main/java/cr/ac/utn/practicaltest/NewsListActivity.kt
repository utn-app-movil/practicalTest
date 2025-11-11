package cr.ac.utn.practicaltest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import cr.ac.utn.practicaltest.MyController.NewsController
import cr.ac.utn.practicaltest.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsListBinding
    private lateinit var controller: NewsController
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Internal News"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        controller = NewsController(this)
        setupRecyclerView()
        loadNews()

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, NewsFormActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter { news ->
            val i = Intent(this, NewsFormActivity::class.java)
            i.putExtra("NEWS_ID", news.ID)
            startActivity(i)
        }
        binding.rvNews.layoutManager = LinearLayoutManager(this)
        binding.rvNews.adapter = adapter
    }

    private fun loadNews() {
        adapter.submitList(controller.getAll())
    }

    override fun onResume() {
        super.onResume()
        loadNews()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}