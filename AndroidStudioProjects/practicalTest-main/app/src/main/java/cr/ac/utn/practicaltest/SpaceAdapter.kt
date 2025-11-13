package cr.ac.utn.practicaltest

import Controller.SpaceController
import Entity.Space
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SpaceAdapter(
    private var spaces: List<Space>,
    private val onItemClick: (Space) -> Unit,
    private val onSpaceDeleted: () -> Unit = {}
) : RecyclerView.Adapter<SpaceAdapter.SpaceViewHolder>() {

    class SpaceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSpaceName: TextView = view.findViewById(R.id.tvSpaceName)
        val tvSpaceType: TextView = view.findViewById(R.id.tvSpaceType)
        val tvSpaceCapacity: TextView = view.findViewById(R.id.tvSpaceCapacity)
        val tvSpacePrice: TextView = view.findViewById(R.id.tvSpacePrice)
        val tvSpaceDescription: TextView = view.findViewById(R.id.tvSpaceDescription)
        val tvSpaceStatus: TextView = view.findViewById(R.id.tvSpaceStatus)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_space, parent, false)
        return SpaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpaceViewHolder, position: Int) {
        val space = spaces[position]

        holder.tvSpaceName.text = space.Name
        holder.tvSpaceType.text = "Tipo: ${space.Type.name}"
        holder.tvSpaceCapacity.text = "Capacidad: ${space.Capacity} personas"
        holder.tvSpacePrice.text = "Precio: $${space.PricePerHour}/hora"
        holder.tvSpaceDescription.text = space.Description

        // Update status
        if (space.Available) {
            holder.tvSpaceStatus.text = "Disponible"
            holder.tvSpaceStatus.setBackgroundColor(
                holder.itemView.context.getColor(android.R.color.holo_green_light)
            )
        } else {
            holder.tvSpaceStatus.text = "Ocupado"
            holder.tvSpaceStatus.setBackgroundColor(
                holder.itemView.context.getColor(android.R.color.holo_red_light)
            )
        }

        // Edit button
        holder.btnEdit.setOnClickListener {
            onItemClick(space)
        }

        // Delete button
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar Espacio")
                .setMessage("¿Está seguro que desea eliminar ${space.Name}?")
                .setPositiveButton("Eliminar") { _, _ ->
                    try {
                        val controller = SpaceController(holder.itemView.context)
                        controller.removeSpace(space.ID)
                        onSpaceDeleted()
                    } catch (e: Exception) {
                        AlertDialog.Builder(holder.itemView.context)
                            .setTitle("Error")
                            .setMessage(e.message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    override fun getItemCount() = spaces.size

    fun updateSpaces(newSpaces: List<Space>) {
        spaces = newSpaces
        notifyDataSetChanged()
    }
}
