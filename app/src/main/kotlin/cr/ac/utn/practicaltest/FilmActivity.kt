package cr.ac.utn.practicaltest

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilmActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDirector: EditText
    private lateinit var editTextYear: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonUpdate: Button
    private lateinit var recyclerViewFilms: RecyclerView

    private lateinit var filmAdapter: FilmAdapter
    private val films = mutableListOf<Film>()
    private var selectedFilm: Film? = null
    private var nextId = 1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDirector = findViewById(R.id.editTextDirector)
        editTextYear = findViewById(R.id.editTextYear)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        recyclerViewFilms = findViewById(R.id.recyclerViewFilms)

        filmAdapter = FilmAdapter(films, onEditClick = { film ->
            selectFilmForEditing(film)
        }, onDeleteClick = { film ->
            deleteFilm(film)
        })

        recyclerViewFilms.layoutManager = LinearLayoutManager(this)
        recyclerViewFilms.adapter = filmAdapter

        buttonAdd.setOnClickListener {
            addFilm()
        }

        buttonUpdate.setOnClickListener {
            updateFilm()
        }
    }

    private fun selectFilmForEditing(film: Film) {
        selectedFilm = film
        editTextTitle.setText(film.title)
        editTextDirector.setText(film.director)
        editTextYear.setText(film.year.toString())
    }

    private fun addFilm() {
        val title = editTextTitle.text.toString()
        val director = editTextDirector.text.toString()
        val year = editTextYear.text.toString().toIntOrNull()

        if (title.isNotEmpty() && director.isNotEmpty() && year != null) {
            val film = Film(nextId++, title, director, year)
            filmAdapter.addFilm(film)
            clearInputFields()
        }
    }

    private fun updateFilm() {
        selectedFilm?.let { film ->
            val title = editTextTitle.text.toString()
            val director = editTextDirector.text.toString()
            val year = editTextYear.text.toString().toIntOrNull()

            if (title.isNotEmpty() && director.isNotEmpty() && year != null) {
                film.title = title
                film.director = director
                film.year = year
                filmAdapter.updateFilm(film)
                clearInputFields()
                selectedFilm = null
            }
        }
    }

    private fun deleteFilm(film: Film) {
        filmAdapter.removeFilm(film)
    }

    private fun clearInputFields() {
        editTextTitle.text.clear()
        editTextDirector.text.clear()
        editTextYear.text.clear()
    }
}
