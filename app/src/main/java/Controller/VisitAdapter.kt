package Controller

import Entity.Visit
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R

class VisitAdapter(
    private var visits: MutableList<Visit>,
    private val onEditClick: (Visit) -> Unit,
    private val onDeleteClick: (Visit) -> Unit
) : RecyclerView.Adapter<VisitAdapter.VisitViewHolder>() {

    class VisitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvResidentName: TextView = itemView.findViewById(R.id.tvResidentName)
        val tvVisitorName: TextView = itemView.findViewById(R.id.tvVisitorName)
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val tvPurpose: TextView = itemView.findViewById(R.id.tvPurpose)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnEdit: Button = itemView.findViewById(R.id.btnEditVisit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteVisit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_visit, parent, false)
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisitViewHolder, position: Int) {
        val visit = visits[position]

        // Adaptado al formato de tu layout
        holder.tvResidentName.text = "Residente: ${visit.residentName}"
        holder.tvVisitorName.text = "Visitante: ${visit.visitorName}"
        holder.tvDateTime.text = "${visit.visitDate} - ${visit.visitTime}"
        holder.tvPurpose.text = "Motivo: ${visit.purpose}"

        // Configurar estado con color
        when (visit.status.lowercase()) {
            "confirmada", "programada" -> {
                holder.tvStatus.text = "Estado: Confirmada"
                holder.tvStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_green_dark))
            }
            "pendiente" -> {
                holder.tvStatus.text = "Estado: Pendiente"
                holder.tvStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_orange_dark))
            }
            "cancelada" -> {
                holder.tvStatus.text = "Estado: Cancelada"
                holder.tvStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_red_dark))
            }
            "completada" -> {
                holder.tvStatus.text = "Estado: Completada"
                holder.tvStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_blue_dark))
            }
            else -> {
                holder.tvStatus.text = "Estado: ${visit.status}"
                holder.tvStatus.setTextColor(holder.itemView.context.getColor(android.R.color.holo_blue_light))
            }
        }

        // Botones
        holder.btnEdit.setOnClickListener {
            onEditClick(visit)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(visit)
        }
    }

    override fun getItemCount(): Int = visits.size

    fun updateVisits(newVisits: List<Visit>) {
        visits.clear()
        visits.addAll(newVisits)
        notifyDataSetChanged()
    }
}