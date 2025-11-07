package cr.ac.utn.practicaltest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import cr.ac.utn.practicaltest.R

data class SimpleRow(val id: Int, val title: String, val subtitle: String)

class SimpleAdapter(
    private val onClick: (SimpleRow) -> Unit
) : RecyclerView.Adapter<SimpleAdapter.VH>() {

    private val data = mutableListOf<SimpleRow>()

    fun submit(list: List<SimpleRow>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun current(): List<SimpleRow> = data.toList()

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        private val t1: TextView = v.findViewById(R.id.rowTitle)
        private val t2: TextView = v.findViewById(R.id.rowSubtitle)
        fun bind(item: SimpleRow) {
            t1.text = "${item.id} • ${item.title}"
            t2.text = item.subtitle
            itemView.setOnClickListener { onClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_simple, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(data[position])
    override fun getItemCount(): Int = data.size
}
