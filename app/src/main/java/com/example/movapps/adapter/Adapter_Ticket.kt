package com.example.movapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movapps.R
import com.example.movapps.model.Ticket
import com.squareup.picasso.Picasso

class Adapter_Ticket(private  var data: List<Ticket>, private val listener: (Ticket) -> Unit): RecyclerView.Adapter<Adapter_Ticket.ViewHolder>() {

    lateinit var contextAdapter:Context

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tv_title:TextView = view.findViewById(R.id.tv_ticket_title)
        private val tv_location:TextView = view.findViewById(R.id.tv_ticket_location)
        private val tv_date:TextView = view.findViewById(R.id.tv_ticket_date)
        private val img_poster:ImageView = view.findViewById(R.id.img_ticket_poster)

        fun bindItem(data: Ticket, listener: (Ticket) -> Unit, context: Context, position: Int) {
            tv_title.text = data.title
            tv_location.text = data.location
            tv_date.text = data.date
            Picasso.get().load(data.backdrop).into(img_poster)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.holder_ticket, parent, false)
        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}