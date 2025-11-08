package Entity

class Inventory {

    private var id: String=""
    private var resourceName: String=""
    private var category: String=""
    private var provider: String=""
    private var quantity: Int = 0

    constructor()

    constructor(id: String, resourceName: String, category: String
            ,quantity: Int, provider: String)
    {
        this.id=id
        this.resourceName=resourceName
        this.category=category
        this.provider=provider
        this.quantity=quantity
    }

    var ID: String
        get() = this.id
        set(value) {this.id=value}

    var ResourceName: String
        get() = this.resourceName
        set(value) {this.resourceName=value}

    var Category: String
        get() = this.category
        set(value) {this.category=value}

    var Provider: String
        get() = this.provider
        set(value) {this.provider=value}

    var Quantity: Int
        get() = this.quantity
        set(value) {this.quantity=value}
}