package Entity

import android.graphics.Bitmap

class Vehicle {
    private var id: String = ""
    private var plateNumber: String = ""//numero de placa
    private var ownerName: String = ""//nombre del propietario
    private var brand: String = ""//marca
    private var model: String = ""//modelo
    private var color: String = ""
    private var photo: Bitmap? = null

    constructor()

    constructor(
        id: String,
        plateNumber: String,
        ownerName: String,
        brand: String,
        model: String,
        color: String,
        photo: Bitmap?
    ) {
        this.id = id
        this.plateNumber = plateNumber
        this.ownerName = ownerName
        this.brand = brand
        this.model = model
        this.color = color
        this.photo = photo
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var PlateNumber: String
        get() = this.plateNumber
        set(value) { this.plateNumber = value }

    var OwnerName: String
        get() = this.ownerName
        set(value) { this.ownerName = value }

    var Brand: String
        get() = this.brand
        set(value) { this.brand = value }

    var Model: String
        get() = this.model
        set(value) { this.model = value }

    var Color: String
        get() = this.color
        set(value) { this.color = value }

    var Photo: Bitmap?
        get() = this.photo
        set(value) { this.photo = value }
}
