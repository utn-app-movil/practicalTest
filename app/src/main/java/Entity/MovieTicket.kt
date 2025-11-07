package Entity

import java.time.LocalDate

class MovieTicket {
    private var id: String = ""
    private var customerName: String = ""
    private var movieTitle: String = ""
    private var showDate: LocalDate = LocalDate.now()
    private var quantity: Int = 0
    private var unitPrice: Double = 0.0
    private var totalPrice: Double = 0.0

    constructor()

    constructor(
        id: String,
        customerName: String,
        movieTitle: String,
        showDate: LocalDate,
        quantity: Int,
        unitPrice: Double,
        totalPrice: Double
    ) {
        this.id = id
        this.customerName = customerName
        this.movieTitle = movieTitle
        this.showDate = showDate
        this.quantity = quantity
        this.unitPrice = unitPrice
        this.totalPrice = totalPrice
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var CustomerName: String
        get() = this.customerName
        set(value) { this.customerName = value }

    var MovieTitle: String
        get() = this.movieTitle
        set(value) { this.movieTitle = value }

    var ShowDate: LocalDate
        get() = this.showDate
        set(value) { this.showDate = value }

    var Quantity: Int
        get() = this.quantity
        set(value) { this.quantity = value }

    var UnitPrice: Double
        get() = this.unitPrice
        set(value) { this.unitPrice = value }

    var TotalPrice: Double
        get() = this.totalPrice
        set(value) { this.totalPrice = value }

    fun calculateTotalPrice() {
        this.totalPrice = this.quantity * this.unitPrice
    }
}