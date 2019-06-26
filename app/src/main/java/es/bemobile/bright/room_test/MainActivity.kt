package es.bemobile.bright.room_test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import es.bemobile.bright.room_test.database.BrightDatabase
import es.bemobile.bright.room_test.database.tables.ChatRemote
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private var database: BrightDatabase? = null
    private val job = Job()
    override val coroutineContext = Dispatchers.IO + job


    val mockLevel = ChatRemote.LevelEntity(1, 1, "mock_level")
    val mockReinforcement = ChatRemote.ReinforcementEntity(1, "reinforcement_mock", "this is a test message")
    val mockRange = ChatRemote.RangeEntity(1, "mockRange", 1)
    val mockTechnique = ChatRemote.TechniqueEntity(1, "mock_technique", "technique_name", "technique_text", 12344567, "", "", "", "button_display", "button_text")
    val mockMessage1 = ChatRemote.MessageEntity(1, 0, "123")
    val mockMessage2 = ChatRemote.MessageEntity(2, 0, "123456")
    val mockMessages = listOf(mockMessage1, mockMessage2)
    val messageModel = ChatRemote.MessageModel(1, 0, "123", mockLevel, mockRange, mockTechnique, mockReinforcement)
    val messageModel2 = ChatRemote.MessageModel(2, 1, "123456", mockLevel, mockRange, mockTechnique, mockReinforcement)

    private var counter = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addBtn.setOnClickListener {
            counter++
            addNewMessage(database!!, counter)
        }

        database = Room.databaseBuilder(this, BrightDatabase::class.java, "test.db").build()



        addNewMessage(database!!, counter)
        database!!.chatDao().getAllMessages().observe(this@MainActivity, Observer { list ->
            if (list.isNullOrEmpty()) return@Observer
            val newList = ArrayList<ChatRemote.MessageModel>()
            list.forEach {
                if (it.level.isEmpty()) return@forEach
                if (it.range.isEmpty()) return@forEach
                if (it.technique.isEmpty()) return@forEach
                if (it.reinforcement.isEmpty()) return@forEach
                val item = ChatRemote.MessageModel(
                    it.message.message.messageId,
                    it.message.message.lastMessage,
                    it.message.message.timestamp,
                    ChatRemote.LevelEntity(it.level[0]),
                    ChatRemote.RangeEntity(it.range[0]),
                    ChatRemote.TechniqueEntity(it.technique[0]),
                    ChatRemote.ReinforcementEntity(it.reinforcement[0])
                )
                newList.add(item)
            }
            val adapter = ItemAdapter(newList)
            itemsView.adapter = adapter
            itemsView.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

        })

    }

    private fun addNewMessage(database: BrightDatabase, index: Int) = launch {
        database.chatDao().insertMessage(ChatRemote.MessageDBEntity(index, mockMessage2))
        database.chatDao().insertLevel(ChatRemote.LevelDBEntity(0, index, mockLevel))
        database.chatDao().insertRange(ChatRemote.RangeDBEntity(0, index, mockRange))
        database.chatDao().insertTechnique(ChatRemote.TechniqueDBEntity(0, index, mockTechnique))
        val mockReinforcement = ChatRemote.ReinforcementEntity(0, "reinforcement_mock", "this is message $index")
        database.chatDao().insertReinforcement(ChatRemote.ReinforcementDBEntity(0, index, mockReinforcement))
    }

}
