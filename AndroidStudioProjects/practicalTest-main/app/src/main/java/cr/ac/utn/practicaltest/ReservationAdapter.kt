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
    private val onItemClick: (Reservation) -> Unit,
    private val onReservationCancelled: () -> Unit = {}
) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {

    class ReservationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvReservationId: TextView = view.findViewById(R.id.tvReservationId)
        val tvReservationStatus: TextView = view.findViewById(R.id.tvReservationStatus)
        val tvSpaceName: TextView = view.findViewById(R.id.tvSpaceName)
        val tvDateTime: TextView = view.findViewById(R.id.tvDateTime)
        val tvPurpose: TextView = view.findViewById(R.id.tvPurpose)
        val tvAttendees: TextView = view.findViewById(R.id.tvAttendees)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
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
        holder.tvDateTime.text = "${reservation.StartDateTime.format(formatter)} - ${reservation.EndDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        holder.tvPurpose.text = "Propósito: ${reservation.Purpose}"
        holder.tvAttendees.text = "Asistentes: ${reservation.NumberOfAttendees}"
        holder.tvPrice.text = "Precio total: $${reservation.TotalPrice}"

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

        // Cancel button
        holder.btnCancel.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Cancelar Reserva")
                .setMessage("¿Está seguro que desea cancelar esta reserva?")
                .setPositiveButton("Cancelar Reserva") { _, _ ->
                    try {
                        val controller = ReservationController(holder.itemView.context)
                        controller.cancelReservation(reservation.ID)
                        onReservationCancelled()
                    } catch (e: Exception) {
                        AlertDialog.Builder(holder.itemView.context)
                            .setTitle("Error")
                            .setMessage(e.message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount() = reservations.size

    fun updateReservations(newReservations: List<Reservation>) {
        reservations = newReservations
        notifyDataSetChanged()
    }
}
