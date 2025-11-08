package Controller

import Data.IBookDataManager
import Data.BookMemoryDataManager
import Entity.Book
import Entity.Person

import android.content.Context
import cr.ac.utn.practicaltest.R

class BookController {
    private var dataManager: BookMemoryDataManager = BookMemoryDataManager
    private  var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addBook(book: Book){
        try {
            dataManager.add(book)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updateBook(book: Book){
        try {
            dataManager.update(book)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getById(bookid: String): Book?{
        try {
            return dataManager.getById(bookid)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByAuthor(authorName: String): Book? {
        try {
            return dataManager.getByAuthorName(authorName)  
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErroMsgGetByAuthor))
        }
    }

    fun getByTitle(title: String): Book? {
        try {
            return dataManager.getByTitle(title)
        } catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetByTitle))
        }
    }

}