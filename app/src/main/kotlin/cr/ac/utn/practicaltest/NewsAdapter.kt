package cr.ac.utn.practicaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.indexOfFirst

class NewsAdapter(
    private val newsList: MutableList<News>,
    private val onEditClick: (News) -> Unit,
    private val onDeleteClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.bind(news)
        holder.itemView.setOnClickListener { onEditClick(news) }
        holder.itemView.setOnLongClickListener {
            onDeleteClick(news)
            true
        }
    }

    override fun getItemCount(): Int = newsList.size

    fun addNews(news: News) {
        newsList.add(news)
        notifyItemInserted(newsList.size - 1)
    }

    fun updateNews(updatedNews: News) {
        val index = newsList.indexOfFirst { it.id == updatedNews.id }
        if (index != -1) {
            newsList[index] = updatedNews
            notifyItemChanged(index)
        }
    }

    fun removeNews(news: News) {
        val index = newsList.indexOfFirst { it.id == news.id }
        if (index != -1) {
            newsList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val contentTextView: TextView = itemView.findViewById(R.id.textViewContent)

        fun bind(news: News) {
            dateTextView.text = news.date
            titleTextView.text = news.title
            contentTextView.text = news.content
        }
    }
}
