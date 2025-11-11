package Entity

class Product {
    private var id: String = ""
    private var name: String = ""
    private var price: Double = 0.0
    private var stock: Int = 0
    private var description: String = ""

    var ID: String
        get() = this.id
        set(value) { this.id = value.trim() }

    var Name: String
        get() = this.name
        set(value) { this.name = value.trim() }

    var Price: Double
        get() = this.price
        set(value) { this.price = value }

    var Stock: Int
        get() = this.stock
        set(value) { this.stock = value }

    var Description: String
        get() = this.description
        set(value) { this.description = value.trim() }
}
