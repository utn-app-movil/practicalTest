package cr.ac.utn.practicaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmAdapter(
    private val films: MutableList<Film>,
    private val onEditClick: (Film) -> Unit,
    private val onDeleteClick: (Film) -> Unit
) : RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.bind(film)
        holder.itemView.setOnClickListener { onEditClick(film) }
        holder.itemView.setOnLongClickListener {
            onDeleteClick(film)
            true
        }
    }

    override fun getItemCount(): Int = films.size

    fun addFilm(film: Film) {
        films.add(film)
        notifyItemInserted(films.size - 1)
    }

    fun updateFilm(updatedFilm: Film) {
        val index = films.indexOfFirst { it.id == updatedFilm.id }
        if (index != -1) {
            films[index] = updatedFilm
            notifyItemChanged(index)
        }
    }

    fun removeFilm(film: Film) {
        val index = films.indexOfFirst { it.id == film.id }
        if (index != -1) {
            films.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        private val directorTextView: TextView = itemView.findViewById(R.id.textViewDirector)
        private val yearTextView: TextView = itemView.findViewById(R.id.textViewYear)

        fun bind(film: Film) {
            titleTextView.text = film.title
            directorTextView.text = film.director
            yearTextView.text = film.year.toString()
        }
    }
}
