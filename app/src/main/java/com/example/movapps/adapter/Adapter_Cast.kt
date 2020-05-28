package com.example.movapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movapps.R
import com.example.movapps.model.Cast
import com.squareup.picasso.Picasso

class Adapter_Cast(private  var data: List<Cast>, private val listener: (Cast) -> Unit): RecyclerView.Adapter<Adapter_Cast.ViewHolder>() {

   lateinit var contextAdapter:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_Cast.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflatedView: View = layoutInflater.inflate(R.layout.holder_cast, parent, false)
        return ViewHolder(inflatedView)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val tv_cast:TextView = view.findViewById(R.id.tv_cast)
        private val img_cast:ImageView = view.findViewById(R.id.img_cast)

        fun bindItem(data: Cast, listener: (Cast) -> Unit, context: Context, position: Int) {
            tv_cast.text = data.name
            Picasso.get().load(data.photo).into(img_cast)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Adapter_Cast.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }
}