package com.example.movapps

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movapps.adapter.Adapter_Film
import com.example.movapps.adapter.Adapter_Upcoming
import com.example.movapps.model.Film
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home.*
import java.lang.StringBuilder

class Home : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var movieList = ArrayList<Film>()
    private var upcomingList = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        img_tickets.setOnClickListener {
            finish()
            val intent = Intent(this@Home, Tickets::class.java)
            startActivity(intent)
        }

        img_settings.setOnClickListener {
            finish()
            val intent = Intent(this@Home, Settings::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                var builder = StringBuilder()
                builder.append("IDR ").append(p0.child("balance").value.toString()).append("K")

                tv_balance.text = builder.toString()
                tv_name.text = p0.child("name").value.toString()

                Picasso.get().load(p0.child("photo").value.toString()).fit().centerCrop().into(img_avatar)

                rv_now_playing.layoutManager = LinearLayoutManager(this@Home, LinearLayoutManager.HORIZONTAL, false)
                mDatabase = FirebaseDatabase.getInstance().reference.child("Movie")
                mDatabase.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_LONG).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        movieList.clear()

                        for(getData in p0.children) {
                            val film = getData.getValue(Film::class.java)
                            movieList.add(film!!)
                        }

                        if(movieList.isNotEmpty()) {
                            rv_now_playing.adapter = Adapter_Film(movieList) {
                                val intent = Intent(this@Home, Movie_Details::class.java).putExtra("data", it).putExtra("flag", true)
                                startActivity(intent)
                            }
                        }
                    }
                })

                rv_coming_soon.layoutManager = LinearLayoutManager(this@Home, LinearLayoutManager.VERTICAL, false)
                mDatabase = FirebaseDatabase.getInstance().reference.child("Upcoming")
                mDatabase.addValueEventListener(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_LONG).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        upcomingList.clear()

                        for(getData in p0.children) {
                            val film = getData.getValue(Film::class.java)
                            upcomingList.add(film!!)
                        }

                        if(upcomingList.isNotEmpty()) {
                            rv_coming_soon.adapter = Adapter_Upcoming(upcomingList) {
                                val intent = Intent(this@Home, Movie_Details::class.java).putExtra("data", it)
                                startActivity(intent)
                            }
                        }
                    }

                })
            }
        })
    }
}
