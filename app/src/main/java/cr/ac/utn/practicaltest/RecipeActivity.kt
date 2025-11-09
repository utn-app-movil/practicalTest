package cr.ac.utn.practicaltest



import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RecipeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddRecipe: FloatingActionButton
    private lateinit var tvEmptyState: TextView
    private lateinit var adapter: RecipeAdapter

    // Lista de recetas en memoria
    private val recipesList = mutableListOf<Recipe>()

    // Launcher para agregar recetas
    private val addRecipeLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val recipe = result.data?.getSerializableExtra(Add_Edit_Recipe_Activity.EXTRA_RECIPE_RESULT) as? Recipe
            val isEdit = result.data?.getBooleanExtra(Add_Edit_Recipe_Activity.EXTRA_IS_EDIT, false) ?: false

            if (recipe != null) {
                if (isEdit) {
                    // Actualizar receta existente
                    val index = recipesList.indexOfFirst { it.id == recipe.id }
                    if (index != -1) {
                        recipesList[index] = recipe
                        adapter.updateRecipes(recipesList)
                        Toast.makeText(this, "Receta actualizada", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Agregar nueva receta
                    recipesList.add(recipe)
                    adapter.updateRecipes(recipesList)
                    Toast.makeText(this, "Receta agregada", Toast.LENGTH_SHORT).show()
                }
                updateEmptyState()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        initializeViews()
        setupRecyclerView()
        setupListeners()
        updateEmptyState()

        // Agregar algunas recetas de ejemplo (opcional, puedes quitarlo)
        addSampleRecipes()
    }

    private fun initializeViews() {
        recyclerView = findViewById(R.id.recyclerViewRecipes)
        fabAddRecipe = findViewById(R.id.fabAddRecipe)
        tvEmptyState = findViewById(R.id.tvEmptyState)
    }

    private fun setupRecyclerView() {
        adapter = RecipeAdapter(
            recipes = recipesList,
            onEditClick = { recipe ->
                editRecipe(recipe)
            },
            onDeleteClick = { recipe ->
                showDeleteConfirmation(recipe)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun setupListeners() {
        fabAddRecipe.setOnClickListener {
            addNewRecipe()
        }
    }

    private fun addNewRecipe() {
        val intent = Intent(this, Add_Edit_Recipe_Activity::class.java)
        addRecipeLauncher.launch(intent)
    }

    private fun editRecipe(recipe: Recipe) {
        val intent = Intent(this, Add_Edit_Recipe_Activity::class.java).apply {
            putExtra(Add_Edit_Recipe_Activity.EXTRA_RECIPE, recipe)
        }
        addRecipeLauncher.launch(intent)
    }

    private fun showDeleteConfirmation(recipe: Recipe) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar Receta")
            .setMessage("¿Estás seguro de que deseas eliminar '${recipe.name}'?")
            .setPositiveButton("Eliminar") { _, _ ->
                deleteRecipe(recipe)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteRecipe(recipe: Recipe) {
        recipesList.remove(recipe)
        adapter.updateRecipes(recipesList)
        updateEmptyState()
        Toast.makeText(this, "Receta eliminada", Toast.LENGTH_SHORT).show()
    }

    private fun updateEmptyState() {
        if (recipesList.isEmpty()) {
            tvEmptyState.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }


    private fun addSampleRecipes() {
        val sampleRecipes = listOf(
            Recipe(
                name = "Gallo Pinto",
                ingredients = "Arroz cocido, frijoles negros, cebolla, chile dulce, culantro, salsa Lizano",
                steps = "1. Picar finamente cebolla y chile dulce\n2. Sofreír en aceite\n3. Agregar frijoles y arroz\n4. Mezclar bien y agregar salsa Lizano\n5. Servir caliente",
                preparationTime = 20,
                servings = 4
            ),
            Recipe(
                name = "Casado Costarricense",
                ingredients = "Arroz, frijoles, plátano maduro, ensalada, carne (pollo, pescado o res), picadillo",
                steps = "1. Cocinar arroz y frijoles\n2. Freír plátano maduro\n3. Preparar ensalada fresca\n4. Cocinar la carne elegida\n5. Preparar picadillo de verduras\n6. Servir todo junto en un plato",
                preparationTime = 45,
                servings = 4
            )
        )

        recipesList.addAll(sampleRecipes)
        adapter.updateRecipes(recipesList)
        updateEmptyState()
    }
}