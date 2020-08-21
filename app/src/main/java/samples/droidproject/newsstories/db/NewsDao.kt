package samples.droidproject.newsstories.db

import androidx.lifecycle.LiveData
import androidx.room.*

import samples.droidproject.newsstories.models.Article

@Dao
interface NewsDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(article: Article)

    @Query("Select * from articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteNews(article: Article)

}