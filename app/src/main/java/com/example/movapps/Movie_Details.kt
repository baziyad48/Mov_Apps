package com.example.movapps

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movapps.adapter.Adapter_Cast
import com.example.movapps.model.Cast
import com.example.movapps.model.Film
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie__details.*

class Movie_Details : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var castList = ArrayList<Cast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie__details)

        val data = intent.getParcelableExtra<Film>("data")

        mDatabase = FirebaseDatabase.getInstance().reference.child("Cast").child(data.title.toString())

        tv_title.text = data.title
        tv_genre.text = data.genre
        tv_rating.text = data.rating
        tv_description.text = data.description

        Picasso.get().load(data.backdrop).into(img_backdrop)

        btn_play.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.trailer))
            startActivity(intent)
        }

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_select.setOnClickListener {
            val intent = Intent(this@Movie_Details, Chair_Selection::class.java).putExtra("data", data)
            startActivity(intent)
        }

        rv_cast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getCast()


    }

    private fun getCast() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@Movie_Details, "Error, please re-open application!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                castList.clear()

                for (getData in p0.children) {
                    val cast = getData.getValue(Cast::class.java)
                    castList.add(cast!!)
                }

                rv_cast.adapter = Adapter_Cast(castList){

                }
            }

        })
    }
}
