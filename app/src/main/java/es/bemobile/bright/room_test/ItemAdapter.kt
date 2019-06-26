package es.bemobile.bright.room_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.bemobile.bright.room_test.database.tables.ChatRemote
import kotlinx.android.synthetic.main.item.view.*

class ItemAdapter(private val items: List<ChatRemote.MessageModel>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item:ChatRemote.MessageModel) {
            itemView.item_tex.text = item.reinforcement.message
        }

    }
}