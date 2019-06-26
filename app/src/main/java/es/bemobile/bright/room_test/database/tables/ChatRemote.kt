package es.bemobile.bright.room_test.database.tables

import androidx.room.*
import java.util.logging.Level
import com.squareup.moshi.Moshi



open class ChatRemote {

    data class ChatRequestEntity (val chatId: Int, val rangeValue: Int, val lang: String = "es")

    data class ChatResponseEntity (val status: Int, val body: BodyEntity)

    data class BodyEntity(val chat: ChatEntity)

    data class ChatEntity (val chatId: Long, val emotionId: Int, val emotionName: String, val timestamp: String,
                           val message: MessageEntity
    )

    data class MessageWithData (@Embedded val message: MessageDBEntity,
                             @Relation (parentColumn = "id", entityColumn = "messageId", entity = LevelDBEntity::class)
                             val level: List<LevelDBEntity>,
                             @Relation (parentColumn = "id", entityColumn = "messageId", entity = RangeDBEntity::class)
                             val range: List<RangeDBEntity>,
                             @Relation (parentColumn = "id", entityColumn = "messageId", entity = TechniqueDBEntity::class)
                             val technique: List<TechniqueDBEntity>,
                             @Relation (parentColumn = "id", entityColumn = "messageId", entity = ReinforcementDBEntity::class)
                             val reinforcement: List<ReinforcementDBEntity>
    )

    @Entity(tableName = "message")
    data class MessageDBEntity (@PrimaryKey(autoGenerate = true) val id: Int, val message:MessageEntity)

    @Entity(tableName = "level", foreignKeys = [ForeignKey(entity=MessageDBEntity::class, parentColumns = ["id"], childColumns = ["messageId"])], indices = [Index("messageId")])
    data class LevelDBEntity(@PrimaryKey(autoGenerate = true) val id: Int, val messageId: Int, val level:LevelEntity)

    @Entity(tableName = "range", foreignKeys = [ForeignKey(entity=MessageDBEntity::class, parentColumns = ["id"], childColumns = ["messageId"])], indices = [Index("messageId")])
    data class RangeDBEntity (@PrimaryKey(autoGenerate = true) val id: Int, val messageId: Int,
                            val range:RangeEntity)
    @Entity(tableName = "technique", foreignKeys = [ForeignKey(entity=MessageDBEntity::class, parentColumns = ["id"], childColumns = ["messageId"])], indices = [Index("messageId")])
    class TechniqueDBEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
                             @ColumnInfo(name = "messageId") val messageId: Int,
                             @ColumnInfo(name = "technique") val technique: TechniqueEntity)

    @Entity(tableName = "reinforcement", foreignKeys = [ForeignKey(entity=MessageDBEntity::class, parentColumns = ["id"], childColumns = ["messageId"])], indices = [Index("messageId")])
    class ReinforcementDBEntity (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
                                 @ColumnInfo(name = "messageId") val messageId: Int,
                                 @ColumnInfo(name = "reinforcement") val reinforcement: ReinforcementEntity)

    data class MessageModel(val messageId: Int, val lastMessage: Long, val timestamp: String, val level:LevelEntity, val range:RangeEntity,
                                     val technique: TechniqueEntity, val reinforcement: ReinforcementEntity)

    data class MessageEntity (val messageId: Int, val lastMessage: Long, val timestamp: String) {
        constructor(fromDB:MessageDBEntity) : this (fromDB.message.messageId, fromDB.message.lastMessage, fromDB.message.timestamp)
    }

    data class LevelEntity (val levelId: Int, val level: Int, val name: String) {
        constructor(fromDB:LevelDBEntity) : this (fromDB.level.levelId, fromDB.level.level, fromDB.level.name)
    }

    data class RangeEntity (val rangeId: Int, val name: String, val range: Int) {
        constructor(fromDB:RangeDBEntity) : this (fromDB.range.rangeId, fromDB.range.name, fromDB.range.range)
    }

    data class TechniqueEntity (val techniqueId : Int, val type: String, val name: String, val text: String,
                               val time: Long, val imageUrl: String, val audioUrl: String, val videoUrl: String,
                               val buttonDisplay: String, val buttonText: String) {
        constructor(fromDB: TechniqueDBEntity) : this (fromDB.technique.techniqueId, fromDB.technique.type, fromDB.technique.name, fromDB.technique.text,
                                                        fromDB.technique.time, fromDB.technique.imageUrl, fromDB.technique.audioUrl, fromDB.technique.videoUrl,
                                                        fromDB.technique.buttonDisplay, fromDB.technique.buttonText)
    }

    data class ReinforcementEntity (val reinforcementId: Int, val type: String, val message: String) {
        constructor(fromDB: ReinforcementDBEntity) : this (fromDB.reinforcement.reinforcementId, fromDB.reinforcement.type, fromDB.reinforcement.message)
    }


    class Converters {
        companion object {

            @TypeConverter
            @JvmStatic
            fun fromMessage(value: MessageEntity): String {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(MessageEntity::class.java)
                return adapter.toJson(value)
            }

            @TypeConverter
            @JvmStatic
            fun toMessage(value: String): MessageEntity {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(MessageEntity::class.java)
                return adapter.fromJson(value)!!
            }

            @TypeConverter
            @JvmStatic
            fun fromLevel(value: LevelEntity): String {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(LevelEntity::class.java)
                return adapter.toJson(value)
            }

            @TypeConverter
            @JvmStatic
            fun toLevel(value: String): LevelEntity {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(LevelEntity::class.java)
                return adapter.fromJson(value)!!
            }

            @TypeConverter
            @JvmStatic
            fun fromTechnique(value: TechniqueEntity): String {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(TechniqueEntity::class.java)
                return adapter.toJson(value)
            }

            @TypeConverter
            @JvmStatic
            fun toTechnique(value: String): TechniqueEntity {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(TechniqueEntity::class.java)
                return adapter.fromJson(value)!!
            }

            @TypeConverter
            @JvmStatic
            fun fromReinforcement(value: ReinforcementEntity): String {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(ReinforcementEntity::class.java)
                return adapter.toJson(value)
            }

            @TypeConverter
            @JvmStatic
            fun toReinforcement(value: String): ReinforcementEntity {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(ReinforcementEntity::class.java)
                return adapter.fromJson(value)!!
            }

            @TypeConverter
            @JvmStatic
            fun fromRange(value: RangeEntity): String {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RangeEntity::class.java)
                return adapter.toJson(value)
            }

            @TypeConverter
            @JvmStatic
            fun toRange(value: String): RangeEntity {
                val moshi = Moshi.Builder().build()
                val adapter = moshi.adapter(RangeEntity::class.java)
                return adapter.fromJson(value)!!
            }
        }
    }

}