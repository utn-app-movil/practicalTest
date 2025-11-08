package cr.ac.utn.practicaltest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NewsActivity : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var recyclerViewNews: RecyclerView

    private lateinit var newsAdapter: NewsAdapter
    private val newsList = mutableListOf<News>()
    private var selectedNews: News? = null
    private var nextId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        editTextDate = findViewById(R.id.editTextDate)
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        recyclerViewNews = findViewById(R.id.recyclerViewNews)

        newsAdapter = NewsAdapter(newsList, onEditClick = { news ->
            selectNewsForEditing(news)
        }, onDeleteClick = { news ->
            deleteNews(news)
        })

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = newsAdapter

        buttonAdd.setOnClickListener {
            addNews()
        }

        buttonUpdate.setOnClickListener {
            updateNews()
        }
    }

    private fun selectNewsForEditing(news: News) {
        selectedNews = news
        editTextDate.setText(news.date)
        editTextTitle.setText(news.title)
        editTextContent.setText(news.content)
    }

    private fun addNews() {
        val date = editTextDate.text.toString()
        val title = editTextTitle.text.toString()
        val content = editTextContent.text.toString()

        if (date.isNotEmpty() && title.isNotEmpty() && content.isNotEmpty()) {
            val news = News(nextId++, date, title, content)
            newsAdapter.addNews(news)
            clearInputFields()
        }
    }

    private fun updateNews() {
        selectedNews?.let { news ->
            val date = editTextDate.text.toString()
            val title = editTextTitle.text.toString()
            val content = editTextContent.text.toString()

            if (date.isNotEmpty() && title.isNotEmpty() && content.isNotEmpty()) {
                news.date = date
                news.title = title
                news.content = content
                newsAdapter.updateNews(news)
                clearInputFields()
                selectedNews = null
            }
        }
    }

    private fun deleteNews(news: News) {
        newsAdapter.removeNews(news)
    }

    private fun clearInputFields() {
        editTextDate.text.clear()
        editTextTitle.text.clear()
        editTextContent.text.clear()
    }
}