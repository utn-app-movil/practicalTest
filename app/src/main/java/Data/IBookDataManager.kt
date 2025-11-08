package Data

import Entity.Book

interface IBookDataManager {
    fun add(book: Book)
    fun update(book: Book)
    fun remove(id: String)
    fun getAll(): List<Book>
    fun getById(id: String): Book?
    fun getByTitle(title: String): Book?

    fun getByAuthorName(authorName: String): Book?
}


