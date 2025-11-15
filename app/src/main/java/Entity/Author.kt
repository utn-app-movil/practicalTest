package Entity

class Author {
    var id: Int = 0
    var nombre: String = ""

    constructor(
        id: Int,
        nombre: String
    ) {
        this.id = id
        this.nombre = nombre
    }

    var Id: Int
        get() = this.id
        set(value) { this.id = value }

    var Nombre: String
        get() = this.nombre
        set(value) { this.nombre = value }
}