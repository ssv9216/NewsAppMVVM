package samples.droidproject.newsstories.repository

import samples.droidproject.newsstories.models.Article
import samples.droidproject.newsstories.api.RetrofitInstance
import samples.droidproject.newsstories.db.NewsRoomDatabase

class NewsRepository (
    val db: NewsRoomDatabase
){
    suspend fun getBreakingNews(countryCode:String, pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)
    suspend fun searchNews(searchQuery:String,pageNumber:Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)

    suspend fun insertArticle(article: Article)  =  db.newsDao().insertNews(article)

    fun getSavedNews() = db.newsDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.newsDao().deleteNews(article)
}