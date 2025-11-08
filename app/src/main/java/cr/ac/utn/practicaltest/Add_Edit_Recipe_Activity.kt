package cr.ac.utn.practicaltest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class Add_Edit_Recipe_Activity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var etName: TextInputEditText
    private lateinit var etIngredients: TextInputEditText
    private lateinit var etSteps: TextInputEditText
    private lateinit var etPreparationTime: TextInputEditText
    private lateinit var etServings: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var btnCancel: Button

    private var recipeToEdit: Recipe? = null
    private var isEditMode = false

    companion object {
        const val EXTRA_RECIPE = "extra_recipe"
        const val EXTRA_RECIPE_RESULT = "extra_recipe_result"
        const val EXTRA_IS_EDIT = "extra_is_edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_recipe)

        initializeViews()
        loadRecipeData()
        setupListeners()
    }

    private fun initializeViews() {
        tvTitle = findViewById(R.id.tvTitle)
        etName = findViewById(R.id.etName)
        etIngredients = findViewById(R.id.etIngredients)
        etSteps = findViewById(R.id.etSteps)
        etPreparationTime = findViewById(R.id.etPreparationTime)
        etServings = findViewById(R.id.etServings)
        btnSave = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)
    }

    private fun loadRecipeData() {

        recipeToEdit = intent.getSerializableExtra(EXTRA_RECIPE) as? Recipe

        if (recipeToEdit != null) {
            isEditMode = true
            tvTitle.text = "Editar Receta"
            btnSave.text = "Actualizar Receta"

            //
            etName.setText(recipeToEdit!!.name)
            etIngredients.setText(recipeToEdit!!.ingredients)
            etSteps.setText(recipeToEdit!!.steps)
            etPreparationTime.setText(recipeToEdit!!.preparationTime.toString())
            etServings.setText(recipeToEdit!!.servings.toString())
        } else {
            isEditMode = false
            tvTitle.text = "Nueva Receta"
            btnSave.text = "Guardar Receta"
        }
    }

    private fun setupListeners() {
        btnSave.setOnClickListener {
            saveRecipe()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun saveRecipe() {
        // Validate field
        val name = etName.text.toString().trim()
        val ingredients = etIngredients.text.toString().trim()
        val steps = etSteps.text.toString().trim()
        val preparationTimeStr = etPreparationTime.text.toString().trim()
        val servingsStr = etServings.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el nombre de la receta", Toast.LENGTH_SHORT).show()
            etName.requestFocus()
            return
        }

        if (ingredients.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa los ingredientes", Toast.LENGTH_SHORT).show()
            etIngredients.requestFocus()
            return
        }

        if (steps.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa los pasos de preparación", Toast.LENGTH_SHORT).show()
            etSteps.requestFocus()
            return
        }

        if (preparationTimeStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el tiempo de preparación", Toast.LENGTH_SHORT).show()
            etPreparationTime.requestFocus()
            return
        }

        if (servingsStr.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa el número de porciones", Toast.LENGTH_SHORT).show()
            etServings.requestFocus()
            return
        }

        val preparationTime = preparationTimeStr.toIntOrNull() ?: 0
        val servings = servingsStr.toIntOrNull() ?: 0

        if (preparationTime <= 0) {
            Toast.makeText(this, "El tiempo debe ser mayor a 0", Toast.LENGTH_SHORT).show()
            return
        }

        if (servings <= 0) {
            Toast.makeText(this, "Las porciones deben ser mayores a 0", Toast.LENGTH_SHORT).show()
            return
        }

        // Create and update recipe
        val recipe = if (isEditMode && recipeToEdit != null) {
            // Actualizar receta existente
            recipeToEdit!!.apply {
                this.name = name
                this.ingredients = ingredients
                this.steps = steps
                this.preparationTime = preparationTime
                this.servings = servings
            }
        } else {
            // create new recipe
            Recipe(
                name = name,
                ingredients = ingredients,
                steps = steps,
                preparationTime = preparationTime,
                servings = servings
            )
        }

        // rebot the result
        val resultIntent = Intent().apply {
            putExtra(EXTRA_RECIPE_RESULT, recipe)
            putExtra(EXTRA_IS_EDIT, isEditMode)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}