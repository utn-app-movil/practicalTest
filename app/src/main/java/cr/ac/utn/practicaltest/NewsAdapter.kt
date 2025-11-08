package cr.ac.utn.practicaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.MyEntity.News

class NewsAdapter(
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var list = listOf<News>()

    fun submitList(newList: List<News>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        private val tvDate = itemView.findViewById<TextView>(R.id.tv_date)

        fun bind(news: News) {
            tvTitle.text = news.Title
            tvDate.text = "Fecha: ${news.Date}"
            itemView.setOnClickListener { onClick(news) }
        }
    }
}