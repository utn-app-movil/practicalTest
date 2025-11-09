package Entity

import android.provider.ContactsContract

class Class {
    private var id_class: String=""
    private var name_class: String=""
    private var description: String=""
    private var availability: Boolean = true

    constructor()

    constructor(id_class: String, name_class: String,
                description: String, availability: Boolean)
    {
        this.id_class=id_class
        this.name_class=name_class
        this.description=description
        this.availability=availability

    }

   var Id_class : String
    get() = this.id_class
    set(value) {this.id_class=value}

    var Name_class: String
    get() = this.name_class
    set(value) {this.name_class=value}

    var Description: String
    get() = this.description
    set(value) {this.description=value}

    var Availability: Boolean
    get() = this.availability
    set(value) {this.availability=value}


    fun Course() = "$this.name_class $this.description $this.availability"
}
//CRUD de clases disponibles, horarios, profesores.