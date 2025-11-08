package Util

import Entity.Service
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R
class ServiceAdapter(
    private var serviceList: MutableList<Service>,
    private val onItemClick: (Service) -> Unit
) : RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtServiceName: TextView = itemView.findViewById(R.id.txtServiceName)
        val txtServiceDescription: TextView = itemView.findViewById(R.id.txtServiceDescription)
        val txtServicePrice: TextView = itemView.findViewById(R.id.txtServicePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = serviceList[position]
        holder.txtServiceName.text = service.name
        holder.txtServiceDescription.text = service.description
        holder.txtServicePrice.text = "₡${service.price}"

        // Acción al hacer clic en el item
        holder.itemView.setOnClickListener {
            onItemClick(service)
        }
    }

    override fun getItemCount(): Int = serviceList.size

    fun updateData(newList: List<Service>) {
        serviceList.clear()
        serviceList.addAll(newList)
        notifyDataSetChanged()
    }
}
