package Entity

enum class SpaceType {
    SALON,
    CANCHA,
    AUDITORIO,
    LABORATORIO,
    GIMNASIO,
    OTROS
}

class Space {
    private var id: String = ""
    private var name: String = ""
    private var type: SpaceType = SpaceType.OTROS
    private var capacity: Int = 0
    private var description: String = ""
    private var available: Boolean = true
    private var pricePerHour: Double = 0.0

    constructor()

    constructor(
        id: String,
        name: String,
        type: SpaceType,
        capacity: Int,
        description: String,
        available: Boolean = true,
        pricePerHour: Double = 0.0
    ) {
        this.id = id
        this.name = name
        this.type = type
        this.capacity = capacity
        this.description = description
        this.available = available
        this.pricePerHour = pricePerHour
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var Type: SpaceType
        get() = this.type
        set(value) { this.type = value }

    var Capacity: Int
        get() = this.capacity
        set(value) { this.capacity = value }

    var Description: String
        get() = this.description
        set(value) { this.description = value }

    var Available: Boolean
        get() = this.available
        set(value) { this.available = value }

    var PricePerHour: Double
        get() = this.pricePerHour
        set(value) { this.pricePerHour = value }

    override fun toString(): String {
        return "$name (${type.name}) - Capacidad: $capacity"
    }
}
