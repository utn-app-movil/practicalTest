package cr.ac.utn.practicaltest

import Controller.ReservationController
import Entity.Reservation
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.format.DateTimeFormatter

class ReservationAdapter(
    private var reservations: List<Reservation>,
    private val onItemClick: (Reservation) -> Unit
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvReservationId: TextView = view.findViewById(R.id.tvReservationId)
        val tvReservationStatus: TextView = view.findViewById(R.id.tvReservationStatus)
        val tvSpaceName: TextView = view.findViewById(R.id.tvSpaceName)
        val tvStartDateTime: TextView = view.findViewById(R.id.tvStartDateTime)
        val tvEndDateTime: TextView = view.findViewById(R.id.tvEndDateTime)
        val tvPurpose: TextView = view.findViewById(R.id.tvPurpose)
        val tvAttendees: TextView = view.findViewById(R.id.tvAttendees)
        val tvTotalPrice: TextView = view.findViewById(R.id.tvTotalPrice)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        holder.tvReservationId.text = "Reserva #${reservation.ID.take(8)}"
        holder.tvReservationStatus.text = reservation.Status.name
        holder.tvSpaceName.text = "Espacio ID: ${reservation.SpaceId.take(8)}"
        holder.tvStartDateTime.text = "Inicio: ${reservation.StartDateTime.format(formatter)}"
        holder.tvEndDateTime.text = "Fin: ${reservation.EndDateTime.format(formatter)}"
        holder.tvPurpose.text = "Propósito: ${reservation.Purpose}"
        holder.tvAttendees.text = "Asistentes: ${reservation.NumberOfAttendees}"
        holder.tvTotalPrice.text = "Total: $${reservation.TotalPrice}"

        // Status color
        when (reservation.Status) {
            Entity.ReservationStatus.CONFIRMADA -> {
                holder.tvReservationStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_green_light)
                )
            }
            Entity.ReservationStatus.PENDIENTE -> {
                holder.tvReservationStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_orange_light)
                )
            }
            Entity.ReservationStatus.CANCELADA -> {
                holder.tvReservationStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_red_light)
                )
            }
            Entity.ReservationStatus.COMPLETADA -> {
                holder.tvReservationStatus.setBackgroundColor(
                    holder.itemView.context.getColor(android.R.color.holo_blue_light)
                )
            }
        }

        // Edit button
        holder.btnEdit.setOnClickListener {
            onItemClick(reservation)
        }

        // Delete button
        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Eliminar Reserva")
                .setMessage("¿Está seguro que desea eliminar esta reserva?")
                .setPositiveButton("Eliminar") { _, _ ->
                    val controller = ReservationController(holder.itemView.context)
                    if (controller.deleteReservation(reservation.ID)) {
                        (holder.itemView.context as? ReservationListActivity)?.onResume()
                    }
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    override fun getItemCount() = reservations.size
}
