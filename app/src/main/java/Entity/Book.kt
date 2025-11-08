package Entity

class Book {
    private var bookId: String=""
    private var bookTitle: String=""
    private var isbn: String=""
    private var bookGenre: String=""
    private var publicationYear: Int=0
    private var publisher: String=""
    private var pageCount: Int=0
    private var available: Boolean=true
    private var authorName: String=""

    constructor()
    constructor(bookId: String, bookTitle: String,
                isbn: String, bookGenre: String,
                publicationYear: Int, publisher: String,
                pageCount: Int, available: Boolean, authorName: String)
    {
        this.bookId=bookId
        this.bookTitle=bookTitle
        this.isbn=isbn
        this.bookGenre=bookGenre
        this.publicationYear=publicationYear
        this.publisher=publisher
        this.pageCount=pageCount
        this.available=available
        this.authorName=authorName
    }

    var BookId: String
        get() = this.bookId
        set(value) {this.bookId=value}
    var BookTitle: String
        get() = this.bookTitle
        set(value) {this.bookTitle=value}
    var ISBN: String
        get() = this.isbn
        set(value) {this.isbn=value}
    var BookGenre: String
        get() = this.bookGenre
        set(value) {this.bookGenre=value}
    var PublicationYear: Int
        get() = this.publicationYear
        set(value) {this.publicationYear=value}
    var Publisher: String
        get() = this.publisher
        set(value) {this.publisher=value}
    var PageCount: Int
        get() = this.pageCount
        set(value) {this.pageCount=value}
    var Available: Boolean
        get() = this.available
        set(value) {this.available=value}
    var AuthorName: String
        get() = this.authorName
        set(value) {this.authorName=value}

}