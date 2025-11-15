package Entity

class Vehicle {

    private var id: String=""
    private var ownerName: String=""
    private var model: String=""
    private var brand: String=""
    private var plate: String=""
    private var year: Int=0
    private var color: String=""

    constructor()

    constructor(id: String, ownerName: String, model: String, brand: String, plate: String,
        year: Int, color: String)
    {
        this.id=id
        this.ownerName=ownerName
        this.model=model
        this.brand=brand
        this.plate=plate
        this.year=year
        this.color=color
    }

    var ID: String
        get() = this.id
        set(value) {this.id=value}

    var OwnerName: String
        get() = this.ownerName
        set(value) {this.ownerName=value}

    var Model: String
        get() = this.model
        set(value) {this.model=value}

    var Brand: String
        get() = this.brand
        set(value) {this.brand=value}

    var Plate: String
        get() = this.plate
        set(value) {this.plate=value}

    var Year: Int
        get() = this.year
        set(value) {this.year=value}

    var Color: String
        get() = this.color
        set(value) {this.color=value}

}