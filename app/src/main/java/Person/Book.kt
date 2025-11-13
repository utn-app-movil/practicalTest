package Person

class Book {
    var id: Int = 0
    var titulo: String = ""
    var autor: Author? = null
    var disponible: Boolean = true

    constructor(
        id: Int,
        titulo: String,
        autor: Author,
        disponible: Boolean
    ) {
        this.id = id
        this.titulo = titulo
        this.autor = autor
        this.disponible = disponible
    }

    var Id: Int
        get() = this.id
        set(value) { this.id = value }

    var Titulo: String
        get() = this.titulo
        set(value) { this.titulo = value }

    var Autor: Author?
        get() = this.autor
        set(value) { this.autor = value }

    var Disponible: Boolean
        get() = this.disponible
        set(value) { this.disponible = value }
}