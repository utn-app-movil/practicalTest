package cr.ac.utn.practicaltest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private var recipes: MutableList<Recipe>,
    private val onEditClick: (Recipe) -> Unit,
    private val onDeleteClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        val tvPreparationTime: TextView = itemView.findViewById(R.id.tvPreparationTime)
        val tvServings: TextView = itemView.findViewById(R.id.tvServings)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.tvRecipeName.text = recipe.name
        holder.tvPreparationTime.text = "⏱️ ${recipe.preparationTime} min"
        holder.tvServings.text = "🍽️ ${recipe.servings} porciones"

        holder.btnEdit.setOnClickListener {
            onEditClick(recipe)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(recipe)
        }
    }

    override fun getItemCount(): Int = recipes.size

    fun updateRecipes(newRecipes: List<Recipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
        notifyDataSetChanged()
    }
}