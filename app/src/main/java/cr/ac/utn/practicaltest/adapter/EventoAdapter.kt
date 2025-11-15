package cr.ac.utn.practicaltest.adapter

import Entity.Evento
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R

class EventoAdapter(
    private val events: List<Evento>,
    private val onClick: (Evento) -> Unit
) : RecyclerView.Adapter<EventoAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvInfo: TextView = itemView.findViewById(R.id.tvInfo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_evento, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val event = events[position]
        holder.tvTitle.text = event.title
        holder.tvInfo.text = "${event.location} - ${event.date ?: "Sin fecha"} ${event.time ?: ""}"
        holder.itemView.setOnClickListener { onClick(event) }
    }

    override fun getItemCount() = events.size
}