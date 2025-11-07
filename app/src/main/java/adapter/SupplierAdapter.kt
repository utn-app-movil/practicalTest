package adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.databinding.SupplierItemBinding
import model.Supplier

class SupplierAdapter(private var suppliers: List<Supplier>) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val binding = SupplierItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SupplierViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        holder.bind(suppliers[position])
    }

    override fun getItemCount() = suppliers.size

    fun updateData(newSuppliers: List<Supplier>) {
        suppliers = newSuppliers
        notifyDataSetChanged()
    }

    class SupplierViewHolder(private val binding: SupplierItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(supplier: Supplier) {
            binding.textViewName.text = supplier.name
            binding.textViewEmail.text = supplier.email
        }
    }
}