package Controller.Incident


import Data.IdataManger
import Entity.Book
import Entity.Loan
import java.util.Date

class LoanController(private val dataManager: IdataManger) {
    fun obtenerPrestamos(): List<Loan> = dataManager.getAllLoans()

    fun obtenerPrestamoPorId(id: Int): Loan? =
        dataManager.getAllLoans().find { it.Id == id }

    fun obtenerPrestamosActivos(): List<Loan> =
        dataManager.getAllLoans().filter { !it.Devuelto }

    fun obtenerPrestamosCompletados(): List<Loan> =
        dataManager.getAllLoans().filter { it.Devuelto }

    fun obtenerPrestamosPorUsuario(nombreUsuario: String): List<Loan> {
        val nombre = nombreUsuario.trim().lowercase()
        return obtenerPrestamos().filter {
            it.Prestatario.lowercase().contains(nombre)
        }
    }

    fun agregarPrestamo(libro: Book, prestatario: String) {
        if (!libro.Disponible) return

        val id = obtenerPrestamos().size + 1
        val nuevoPrestamo = Loan(id, libro, prestatario, Date(), false)
        dataManager.addLoan(nuevoPrestamo)

        // marcar libro como no disponible
        libro.Disponible = false
        dataManager.updateBook(libro)
    }

    fun devolverLibro(prestamo: Loan) {
        prestamo.Devuelto = true
        prestamo.Libro?.Disponible = true
        dataManager.updateBook(prestamo.Libro!!)
    }

    fun calcularProgresoPrestamos(): Int {
        val total = obtenerPrestamos().size
        if (total == 0) return 0
        val completados = obtenerPrestamosCompletados().size
        return ((completados.toFloat() / total) * 100).toInt()
    }

    fun obtenerTotalPrestamos(): Int = obtenerPrestamos().size

    fun hayPrestamos(): Boolean = obtenerPrestamos().isNotEmpty()
}