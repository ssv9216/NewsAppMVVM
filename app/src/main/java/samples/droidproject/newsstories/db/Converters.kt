package samples.droidproject.newsstories.db

import androidx.room.TypeConverters
import samples.droidproject.newsstories.models.Source


class Converters {
    @TypeConverters
    fun fromSource(source: Source):String{
        return source.name
    }

    @TypeConverters
    fun toSource(name:String):Source{
        return Source(name,name)
    }
}