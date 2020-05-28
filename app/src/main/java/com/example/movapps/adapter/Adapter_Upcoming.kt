package com.example.movapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movapps.R
import com.example.movapps.model.Film
import com.squareup.picasso.Picasso

class Adapter_Upcoming(private var data: List<Film>, private val listener: (Film) -> Unit):
    RecyclerView.Adapter<Adapter_Upcoming.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView = layoutInflater.inflate(R.layout.holder_upcoming, parent, false)
        return  ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tv_title: TextView = view.findViewById(R.id.tv_upcoming_title)
        private val tv_genre: TextView = view.findViewById(R.id.tv_upcoming_genre)
        private val img_poster: ImageView = view.findViewById(R.id.img_upcoming_poster)

        fun bindItem(data: Film, listener: (Film) -> Unit, context: Context, position: Int) {
            tv_title.text = data.title
            tv_genre.text = data.genre

            Picasso.get().load(data.backdrop).into(img_poster)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}