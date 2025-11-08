package cr.ac.utn.practicaltest

import Controller.RecipeController
import Entity.Ingredient
import Entity.Recipe
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecipeActivity : AppCompatActivity() {

    lateinit var id: EditText
    lateinit var name: EditText
    lateinit var description: EditText
    lateinit var prepTime: EditText
    lateinit var cookTime: EditText
    lateinit var difficulty: Spinner
    lateinit var servings: EditText
    lateinit var category: EditText
    lateinit var steps: EditText

    lateinit var recipeController: RecipeController
    private var isEditMode: Boolean = false
    private lateinit var menuItemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recipe)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initializing controller
        recipeController = RecipeController(this)

        //  initializing all the main functions
        setterOfFields()
        setupIngredientsSection()
        setupDifficultySpinner()
        setupSearchButton()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete = menu.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mnu_save -> {
                if (isEditMode) {
                    Util.Util.showDialogCondition(
                        this,
                        getString(R.string.TextSaveActionQuestion),
                        { saveRecipe() })
                } else {
                    saveRecipe()
                }
                true
            }

            R.id.mnu_delete -> {
                Util.Util.showDialogCondition(
                    this,
                    getString(R.string.TextDeleteActionQuestion),
                    { deleteRecipe() })
                true
            }

            R.id.mnu_cancel -> {
                clearForm()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupSearchButton() {
        val btnSearch = findViewById<ImageButton>(R.id.btnSearchid_person)
        btnSearch.setOnClickListener {
            val searchId = id.text.toString().trim()
            if (searchId.isNotEmpty()) {
                searchRecipe(searchId)
            } else {
                Toast.makeText(this, "Ingrese un ID para buscar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validationData(): Boolean {
        return id.text.trim().isNotEmpty() &&
                name.text.trim().isNotEmpty() &&
                description.text.trim().isNotEmpty() &&
                prepTime.text.trim().isNotEmpty() &&
                cookTime.text.trim().isNotEmpty() &&
                servings.text.trim().isNotEmpty() &&
                category.text.trim().isNotEmpty() &&
                steps.text.trim().isNotEmpty()
    }

    private fun setterOfFields() {
        difficulty = findViewById(R.id.spinnerDifficulty)
        id = findViewById(R.id.inputId)
        name = findViewById(R.id.inputName)
        description = findViewById(R.id.inputDescription)
        prepTime = findViewById(R.id.inputPrepTime)
        cookTime = findViewById(R.id.inputCookTime)
        servings = findViewById(R.id.inputServings)
        category = findViewById(R.id.inputCategory)
        steps = findViewById(R.id.inputSteps)
    }

    private fun searchRecipe(Id: String) {
        try {
            val recipe = recipeController.getById(Id)
            if (recipe != null) {
                isEditMode = true
                id.setText(recipe.id)
                id.isEnabled = false
                name.setText(recipe.name)
                description.setText(recipe.description)
                prepTime.setText(recipe.prepTime.toString())
                cookTime.setText(recipe.cookTime.toString())
                servings.setText(recipe.servings.toString())
                category.setText(recipe.category)
                steps.setText(recipe.steps)


                val difficulties = arrayOf("Fácil", "Medio", "Difícil")
                val position = difficulties.indexOf(recipe.difficulty)
                if (position != -1) {
                    difficulty.setSelection(position)
                }

                // loading ingredients
                loadIngredients(recipe.ingredients)

                menuItemDelete.isVisible = true
                invalidateOptionsMenu() // update the menu
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            clearForm()
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun loadIngredients(ingredients: List<Ingredient>) {
        val container = findViewById<LinearLayout>(R.id.ingredientsContainer)
        container.removeAllViews()

        ingredients.forEach { ingredient ->
            addIngredientField(container, ingredient)
        }
    }

    private fun addIngredientField(container: LinearLayout, ingredient: Ingredient? = null) {
        val ingredientView = LayoutInflater.from(this).inflate(R.layout.ingredient_item, container, false)

        val nameInput = ingredientView.findViewById<EditText>(R.id.inputIngredientName)
        val quantityInput = ingredientView.findViewById<EditText>(R.id.inputIngredientQuantity)
        val unitInput = ingredientView.findViewById<EditText>(R.id.inputIngredientUnit)
        val stateInput = ingredientView.findViewById<EditText>(R.id.inputIngredientState)
        val notesInput = ingredientView.findViewById<EditText>(R.id.inputIngredientNotes)
        val optionalCheckbox = ingredientView.findViewById<CheckBox>(R.id.checkOptional)

        // if there is a ingredient, fill all the blank spaces
        ingredient?.let {
            nameInput.setText(it.name)
            quantityInput.setText(it.amount.toString())
            unitInput.setText(it.unit)
            stateInput.setText(it.state)
            notesInput.setText(it.notes)
            optionalCheckbox.isChecked = it.isOptional
        }

        val btnRemove = ingredientView.findViewById<ImageButton>(R.id.btnRemoveIngredient)
        btnRemove.setOnClickListener {
            container.removeView(ingredientView)
        }

        container.addView(ingredientView)
    }

    private fun saveRecipe() {
        try {
            if (validationData()) {
                if (recipeController.getById(id.text.toString().trim()) != null && !isEditMode) {
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate), Toast.LENGTH_LONG).show()
                } else {
                    val recipe = getRecipeFromForm()

                    if (!isEditMode) {
                        recipeController.addRecipe(recipe)
                    } else {
                        recipeController.updateRecipe(recipe)
                    }

                    clearForm()
                    Toast.makeText(this, getString(R.string.MsgSaveSuccess), Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteRecipe() {
        try {
            recipeController.removeRecipe(id.text.trim().toString())
            clearForm()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess), Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error al eliminar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupDifficultySpinner() {
        val difficulties = arrayOf("Fácil", "Medio", "Difícil")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulties)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        difficulty.adapter = adapter
        difficulty.setSelection(0)
    }

    private fun setupIngredientsSection() {
        val btnAddIngredient = findViewById<Button>(R.id.btnAddIngredient)
        val ingredientsContainer = findViewById<LinearLayout>(R.id.ingredientsContainer)

        btnAddIngredient.setOnClickListener {
            addIngredientField(ingredientsContainer)
        }

        // Adding first empty field
        addIngredientField(ingredientsContainer)
    }

    private fun getIngredientsFromForm(): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        val container = findViewById<LinearLayout>(R.id.ingredientsContainer)

        for (i in 0 until container.childCount) {
            val ingredientView = container.getChildAt(i)
            val nameInput = ingredientView.findViewById<EditText>(R.id.inputIngredientName)
            val quantityInput = ingredientView.findViewById<EditText>(R.id.inputIngredientQuantity)
            val unitInput = ingredientView.findViewById<EditText>(R.id.inputIngredientUnit)
            val stateInput = ingredientView.findViewById<EditText>(R.id.inputIngredientState)
            val notesInput = ingredientView.findViewById<EditText>(R.id.inputIngredientNotes)
            val optionalCheckbox = ingredientView.findViewById<CheckBox>(R.id.checkOptional)

            val name = nameInput.text.toString()
            val quantity = quantityInput.text.toString().toDoubleOrNull() ?: 0.0
            val unit = unitInput.text.toString()
            val state = stateInput.text.toString()
            val notes = notesInput.text.toString()
            val isOptional = optionalCheckbox.isChecked

            if (name.isNotBlank()) {
                ingredients.add(Ingredient(
                    name = name,
                    amount = quantity,
                    unit = unit,
                    state = state,
                    isOptional = isOptional,
                    notes = notes
                ))
            }
        }

        return ingredients
    }

    private fun getRecipeFromForm(): Recipe {
        val ingredients = getIngredientsFromForm()

        return Recipe(
            id = id.text.toString(),
            name = name.text.toString(),
            description = description.text.toString(),
            prepTime = prepTime.text.toString().toIntOrNull() ?: 0,
            cookTime = cookTime.text.toString().toIntOrNull() ?: 0,
            difficulty = difficulty.selectedItem.toString(),
            servings = servings.text.toString().toIntOrNull() ?: 1,
            category = category.text.toString(),
            steps = steps.text.toString()
        ).apply {
            this.ingredients.addAll(ingredients)
        }
    }

    private fun clearForm() {
        id.text.clear()
        name.text.clear()
        description.text.clear()
        prepTime.text.clear()
        cookTime.text.clear()
        difficulty.setSelection(0)
        servings.text.clear()
        category.text.clear()
        steps.text.clear()

        id.isEnabled = true
        isEditMode = false
        menuItemDelete.isVisible = false
        invalidateOptionsMenu()

        val ingredientsContainer = findViewById<LinearLayout>(R.id.ingredientsContainer)
        ingredientsContainer.removeAllViews()
        addIngredientField(ingredientsContainer)
    }
}