package Data

import Entity.Book
import Data.IBookDataManager

object BookMemoryDataManager: IBookDataManager {
    private var bookList = mutableListOf<Book>()

    override fun add(book: Book) {
        bookList.add(book)
    }


    override fun remove (id: String) {

        bookList.removeIf { it.BookId.trim() == id.trim() }
    }

    override fun update(book: Book) {

        remove(book.BookId)
        add(book)
    }

    override fun getAll()= bookList


    override fun getById(id: String): Book? {

        return bookList.find { it.BookId.trim() == id.trim() }
    }

    override fun getByTitle(title: String): Book? {

        return bookList.find { it.BookTitle.trim() == title.trim() }
    }

    override fun getByAuthorName(authorName: String): Book? {

        return bookList.find { it.AuthorName.equals(authorName, ignoreCase = true) }
    }
}
