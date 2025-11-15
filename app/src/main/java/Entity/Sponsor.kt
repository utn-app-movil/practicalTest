package Entity

class Sponsor {
    /*- id
    - nombre_empresa *
    - tipo_apoyo * (ECONOMICO | MATERIAL | SERVICIO)
    - contacto_nombre *
    - contacto_correo *
    - contacto_telefono ?
    - detalle_apoyo ?
    */
    private var _id: String=""
    private var _nameSponsor: String=""
    private var _type: String=""
    private var _contactName: String=""
    private var _contactEmail: String=""
    private var _contactPhone: String=""
    private var _detail: String?=null

    //Constructor vacido
    constructor()

    //Contructor
    constructor(id: String, nameSponsor: String, type: String, contactName: String,
                contactEmail: String, contactPhone: String, detail: String){
        this._id=id
        this._nameSponsor=nameSponsor
        this._type=type
        this._contactName=contactName
        this._contactEmail=contactEmail
        this._contactPhone=contactPhone
        this._detail=detail
    }

    // Getters y Setters
    var id: String
        get() = this._id
        set(value) {this._id=value}

    var nameSponsor: String
        get() = this._nameSponsor
        set(value) {this._nameSponsor=value}

    var type: String
        get() = this._type
        set(value) {this._type=value}

    var contactName: String
        get() = this._contactName
        set(value) {this._contactName=value}

    var contactEmail: String
        get() = this._contactEmail
        set(value) {this._contactEmail=value}

    var contactPhone: String
        get() = this._contactPhone
        set(value) {this._contactPhone=value}

    var detail: String?
        get() = this._detail
        set(value) {this._detail=value}
}