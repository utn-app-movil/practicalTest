package cr.ac.utn.practicaltest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cr.ac.utn.practicaltest.MyController.NewsController
import cr.ac.utn.practicaltest.MyEntity.News
import cr.ac.utn.practicaltest.MyUtil.NewsUtil
import cr.ac.utn.practicaltest.databinding.ActivityNewsFormBinding

class NewsFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsFormBinding
    private lateinit var controller: NewsController
    private var currentId: String? = null
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        controller = NewsController(this)
        currentId = intent.getStringExtra("NEWS_ID")
        isEditMode = currentId != null

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = if (isEditMode) "Edit news" else "New news"

        if (isEditMode) loadNews()
    }

    private fun loadNews() {
        val news = controller.getById(currentId!!)
        news?.let {
            binding.etTitle.setText(it.Title)
            binding.etDate.setText(it.Date.toString())
            binding.etContent.setText(it.Content)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        menu.findItem(R.id.mnu_delete).isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> { NewsUtil.showConfirm(this, "¿Save?") { saveNews() }; true }
            R.id.mnu_delete -> { NewsUtil.showConfirm(this, "¿Delete?") { deleteNews() }; true }
            R.id.mnu_cancel, android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNews() {
        val title = binding.etTitle.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(this, "Title needed", Toast.LENGTH_SHORT).show()
            return
        }

        val news = News().apply {
            ID = if (isEditMode) currentId!! else System.currentTimeMillis().toString()
            Title = title
            Date = binding.etDate.text.toString().toIntOrNull() ?: 0
            Content = binding.etContent.text.toString()
        }

        if (isEditMode) controller.update(news)
        else controller.add(news)

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun deleteNews() {
        controller.remove(currentId!!)
        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        finish()
    }
}