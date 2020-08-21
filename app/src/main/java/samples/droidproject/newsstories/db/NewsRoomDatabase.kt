package samples.droidproject.newsstories.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import samples.droidproject.newsstories.models.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class NewsRoomDatabase:RoomDatabase(){
    abstract fun newsDao() : NewsDao

    companion object{
        @Volatile
        private var instance:NewsRoomDatabase?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also{
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsRoomDatabase::class.java,
            "article_db.db"
        ).build()
    }
}