package es.bemobile.bright.room_test.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import es.bemobile.bright.room_test.database.tables.ChatRemote

@Dao
interface ChatDao {



    @Insert(onConflict = REPLACE)
    fun insertMessage(message: ChatRemote.MessageDBEntity)

    @Insert(onConflict = REPLACE)
    fun insertRange(range: ChatRemote.RangeDBEntity)

    @Insert(onConflict = REPLACE)
    fun insertTechnique(technique: ChatRemote.TechniqueDBEntity)

    @Insert(onConflict = REPLACE)
    fun insertLevel(message: ChatRemote.LevelDBEntity)

    @Insert(onConflict = REPLACE)
    fun insertReinforcement(message: ChatRemote.ReinforcementDBEntity)

    @Transaction
    @Query("Select * from message")
    fun getAllMessages(): LiveData<List<ChatRemote.MessageWithData>>



}