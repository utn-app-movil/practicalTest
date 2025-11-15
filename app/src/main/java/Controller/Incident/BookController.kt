package Controller.Incident

import Data.IDataManager
import Entity.Book

class BookController(private val dataManager: IDataManager) {
    fun obtenerLibros(): List<Book> = dataManager.getAllBooks()

    fun obtenerLibroPorId(id: Int): Book? =
        dataManager.getAllBooks().find { it.Id == id }

    fun buscarLibroPorTitulo(titulo: String): List<Book> {
        val textoBusqueda = titulo.trim().lowercase()
        return obtenerLibros().filter {
            it.Titulo.lowercase().contains(textoBusqueda)
        }
    }

    fun obtenerLibrosDisponibles(): List<Book> =
        obtenerLibros().filter { it.Disponible }

    fun obtenerLibrosNoDisponibles(): List<Book> =
        obtenerLibros().filter { !it.Disponible }

    fun obtenerLibroAleatorio(): Book? {
        val libros = obtenerLibros()
        return if (libros.isNotEmpty()) libros.random() else null
    }

    fun agregarLibro(libro: Book) {
        dataManager.addBook(libro)
    }

    fun actualizarLibro(libro: Book) {
        dataManager.updateBook(libro)
    }

    fun eliminarLibro(id: Int) {
        dataManager.deleteBook(id)
    }

    fun verificarDisponibilidad(id: Int): Boolean {
        val libro = obtenerLibroPorId(id)
        return libro?.Disponible ?: false
    }

    fun hayLibros(): Boolean = obtenerLibros().isNotEmpty()

    fun obtenerTotalLibros(): Int = obtenerLibros().size
}