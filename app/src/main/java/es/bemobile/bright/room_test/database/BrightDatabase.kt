package es.bemobile.bright.room_test.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.bemobile.bright.room_test.database.dao.ChatDao
import es.bemobile.bright.room_test.database.tables.ChatRemote


@Database(entities = [ChatRemote.MessageDBEntity::class, ChatRemote.LevelDBEntity::class,
                    ChatRemote.RangeDBEntity::class, ChatRemote.TechniqueDBEntity::class,
                    ChatRemote.ReinforcementDBEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(ChatRemote.Converters::class)
abstract class BrightDatabase : RoomDatabase() {
    abstract fun chatDao(): ChatDao
}