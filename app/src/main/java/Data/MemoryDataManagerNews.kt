package Data

object MemoryDataManagerNews: IDataManagerNews {

    private var newsList = mutableListOf<News>()

    override fun add(news: News) {
        newsList.add(news)
    }

    override fun remove(id: String) {
        newsList.removeIf { it.ID.trim() == id.trim() }
    }

    override fun update(news: News) {
        remove(news.ID)
        add(news)
    }

    override fun getAll() = newsList

    override fun getById(id: String): News? {
        val result = newsList
            .filter { it.ID.trim() == id.trim() }
        return if (result.any()) result[0] else null
    }

    override fun getByTitle(title: String): News? {
        val result = newsList
            .filter { it.Title.trim().equals(title.trim(), ignoreCase = true) }
        return if (result.any()) result[0] else null
    }
}
