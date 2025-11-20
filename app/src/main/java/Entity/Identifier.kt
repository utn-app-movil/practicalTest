package Entity

abstract class Identifier {
    private var _id: String =""

    var ID: String
        get() = this._id
        set(value) {this._id = value}

    abstract val FullName: String
    abstract val FullDescription: String
}