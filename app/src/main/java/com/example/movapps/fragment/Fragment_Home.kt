package com.example.movapps.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movapps.Movie_Details
import com.example.movapps.R
import com.example.movapps.adapter.Adapter_Film
import com.example.movapps.adapter.Adapter_Upcoming
import com.example.movapps.model.Film
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment__home.*
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Home : Fragment() {

    lateinit var mDatabase: DatabaseReference
    private var movieList = ArrayList<Film>()
    private var upcomingList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.activity!!.getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)

        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                var builder = StringBuilder()
                builder.append("IDR ").append(p0.child("balance").value.toString()).append("K")
                tv_balance.text = builder.toString()
                tv_name.text = p0.child("name").value.toString()

                Picasso.get().load(p0.child("photo").value.toString()).fit().centerCrop().into(img_avatar)

                rv_now_playing.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                getMovie()

                rv_coming_soon.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                getUpcoming()
            }
        })
    }

    private fun getUpcoming() {
        mDatabase = FirebaseDatabase.getInstance().reference.child("Upcoming")
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "Error, please re-open application!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                upcomingList.clear()

                for(getData in p0.children) {
                    val film = getData.getValue(Film::class.java)
                    upcomingList.add(film!!)
                }

                if(upcomingList.isNotEmpty()) {
                    rv_coming_soon.adapter = Adapter_Upcoming(upcomingList) {
                        val intent = Intent(context, Movie_Details::class.java).putExtra("data", it)
                        startActivity(intent)
                    }
                }
            }

        })
    }

    private fun getMovie() {
        mDatabase = FirebaseDatabase.getInstance().reference.child("Movie")
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "Error, please re-open application!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                movieList.clear()

                for(getData in p0.children) {
                    val film = getData.getValue(Film::class.java)
                    movieList.add(film!!)
                }

                if(movieList.isNotEmpty()) {
                    rv_now_playing.adapter = Adapter_Film(movieList) {
                        val intent = Intent(context, Movie_Details::class.java).putExtra("data", it).putExtra("flag", true)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}


