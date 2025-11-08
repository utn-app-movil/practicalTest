
package cr.ac.utn.practicaltest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R
import cr.ac.utn.practicaltest.model.Event

class EventAdapter(
    private val onEdit: (Event) -> Unit,
    private val onDelete: (Event) -> Unit
) : ListAdapter<Event, EventAdapter.EventViewHolder>(EventDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)
        private val btnEdit: TextView = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: TextView = itemView.findViewById(R.id.btnDelete)

        fun bind(event: Event) {
            tvTitle.text = event.title
            tvDate.text = event.date
            tvLocation.text = event.location
            btnEdit.setOnClickListener { onEdit(event) }
            btnDelete.setOnClickListener { onDelete(event) }
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem == newItem
}