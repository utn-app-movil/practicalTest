package Entity

import java.util.Date

class Loan {
    var id: Int = 0
    var libro: Book? = null
    var prestatario: String = ""
    var fecha: Date = Date()
    var devuelto: Boolean = false

    constructor(
        id: Int,
        libro: Book,
        prestatario: String,
        fecha: Date,
        devuelto: Boolean
    ) {
        this.id = id
        this.libro = libro
        this.prestatario = prestatario
        this.fecha = fecha
        this.devuelto = devuelto
    }

    var Id: Int
        get() = this.id
        set(value) { this.id = value }

    var Libro: Book?
        get() = this.libro
        set(value) { this.libro = value }

    var Prestatario: String
        get() = this.prestatario
        set(value) { this.prestatario = value }

    var Fecha: Date
        get() = this.fecha
        set(value) { this.fecha = value }

    var Devuelto: Boolean
        get() = this.devuelto
        set(value) { this.devuelto = value }

}