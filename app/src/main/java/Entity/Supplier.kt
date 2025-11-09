package Entity

class Supplier {
    private var id: String = ""
    private var name: String = ""
    private var phone: Int = 0
    private var email: String = ""
    private var contact_name: String = ""
    private var address: String = ""
    private var country: String = ""
    private var city: String = ""
    private var post_code: String = ""

    constructor()

    constructor(
        id: String, name: String, phone: Int, email: String, contact_name: String,
        address: String, country: String, city: String, post_code: String
    ) {
        this.id = id
        this.name = name
        this.phone = phone
        this.email = email
        this.contact_name = contact_name
        this.address = address
        this.country = country
        this.city = city
        this.post_code = post_code
    }

    var ID: String
        get() = this.id
        set(value) {
            this.id = value
        }

    var Name: String
        get() = this.name
        set(value) {
            this.name = value
        }

    var Phone: Int
        get() = this.phone
        set(value) {
            this.phone = value
        }

    var Email: String
        get() = this.email
        set(value) {
            this.email = value
        }

    var Contact_name: String
        get() = this.contact_name
        set(value) {
            this.contact_name = value
        }

    var Address: String
        get() = this.address
        set(value) {
            this.address = value
        }

    var Country: String
        get() = this.country
        set(value) {
            this.country = value
        }

    var City: String
        get() = this.city
        set(value) {
            this.city = value
        }

    var Post_code: String
        get() = this.post_code
        set(value) {
            this.post_code = value
        }

}