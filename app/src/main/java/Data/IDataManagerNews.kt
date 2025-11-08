package Data

interface IDataManagerNews {
    fun add(news: News)
    fun update(news: News)
    fun remove(id: String)
    fun getAll(): List<News>
    fun getById(id: String): News?
    fun getByTitle(title: String): News?
}
