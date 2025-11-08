package cr.ac.utn.practicaltest

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import Controller.BookController
import Entity.Book
import android.annotation.SuppressLint
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LibraryActivity : AppCompatActivity() {

    private fun clearFields() {
        etBookId.text.clear()
        etBookTitle.text.clear()
        etAuthorName.text.clear()
        etISBN.text.clear()
        etGenre.text.clear()
        etPublicationYear.text.clear()
        etPublisher.text.clear()
        etPageCount.text.clear()
        cbAvailable.isChecked = true
    }


    private lateinit var bookController: BookController

    private lateinit var etBookId: EditText
    private lateinit var etBookTitle: EditText
    private lateinit var etISBN: EditText
    private lateinit var etGenre: EditText
    private lateinit var etPublicationYear: EditText
    private lateinit var etPublisher: EditText
    private lateinit var etPageCount: EditText
    private lateinit var cbAvailable: CheckBox

    private lateinit var etAuthorName: EditText
    private lateinit var btnLoadBook: Button
    private lateinit var btnSaveBook: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        val rootView = findViewById<ScrollView>(R.id.root_scroll_view)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            WindowInsetsCompat.CONSUMED
        }

        // Instanciar controller
        bookController = BookController(this)

        etBookId = findViewById(R.id.etBookId)
        etBookTitle = findViewById(R.id.etBookTitle)
        etISBN = findViewById(R.id.etISBN)
        etGenre = findViewById(R.id.etGenre)
        etPublicationYear = findViewById(R.id.etPublicationYear)
        etPublisher = findViewById(R.id.etPublisher)
        etPageCount = findViewById(R.id.etPageCount)
        cbAvailable = findViewById(R.id.cbAvailable)
        etAuthorName = findViewById(R.id.etAuthorName)
        btnLoadBook = findViewById(R.id.btnLoadBook)
        btnSaveBook = findViewById(R.id.btnSaveBook)

        btnLoadBook.setOnClickListener {
            val id = etBookId.text.toString().trim()
            if (id.isEmpty()) {
                Toast.makeText(this, "Please enter a Book ID to load", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val existingBook = bookController.getById(id)
            if (existingBook != null) {
                // Si el libro existe, llena TODOS los campos
                etBookTitle.setText(existingBook.BookTitle)
                etAuthorName.setText(existingBook.AuthorName)
                etISBN.setText(existingBook.ISBN)
                etGenre.setText(existingBook.BookGenre)
                etPublicationYear.setText(existingBook.PublicationYear.toString())
                etPublisher.setText(existingBook.Publisher)
                etPageCount.setText(existingBook.PageCount.toString())
                cbAvailable.isChecked = existingBook.Available
                Toast.makeText(this, "Book loaded", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Book with ID '$id' not found. You can add it.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        btnSaveBook.setOnClickListener {
            val id = etBookId.text.toString().trim()
            val title = etBookTitle.text.toString().trim()

            if (id.isEmpty() || title.isEmpty()) {
                Toast.makeText(this, "Book ID and Title are required to save", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            // Crea un objeto Book con los datos ACTUALES de la pantalla
            val bookFromScreen = Book(
                bookId = id,
                bookTitle = title,
                isbn = etISBN.text.toString(),
                bookGenre = etGenre.text.toString(),
                publicationYear = etPublicationYear.text.toString().toIntOrNull() ?: 0,
                publisher = etPublisher.text.toString(),
                pageCount = etPageCount.text.toString().toIntOrNull() ?: 0,
                available = cbAvailable.isChecked,
                authorName = etAuthorName.text.toString()
            )

            // Decide si actualizar o agregar
            val existingBook = bookController.getById(id)
            if (existingBook != null) {
                // El libro ya existe -> Actualizar
                bookController.updateBook(bookFromScreen)
                Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                // El libro es nuevo -> Agregar
                bookController.addBook(bookFromScreen)
                Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show()


                clearFields()
            }
        }
    }

}
