package Entity

import java.time.LocalDate
import java.util.Date

class Receipt {

    private var id: String=""
    private var number: String=""
    private var client: String=""
    private var amount: Double=0.0
    private lateinit var date: LocalDate



    constructor()

    constructor(id: String, number: String, client: String, amount: Double, date: LocalDate)



    {
        this.id = id
        this.number = number
        this.client = client
        this.amount = amount
        this.date = date

    }

    var ID: String
        get() = this.id
        set(value) {this.id=value}

    var Number: String
        get() = this.number
        set(value) {this.number=value}

    var Client: String
        get() = this.client
        set(value) {this.client=value}

    var Amount: Double
        get() = this.amount
        set(value) {this.amount=value}

    var Date: LocalDate
        get() = this.date
        set(value) {this.date=value}


}