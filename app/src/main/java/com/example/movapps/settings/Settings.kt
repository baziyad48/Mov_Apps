package com.example.movapps.settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movapps.R
import com.example.movapps.Wallet
import com.example.movapps.checkout.Home
import com.example.movapps.my_ticket.Tickets
import com.example.movapps.onboarding.Splash_Screen
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.settings.*
import kotlinx.android.synthetic.main.settings.img_avatar
import kotlinx.android.synthetic.main.settings.img_home
import kotlinx.android.synthetic.main.settings.img_tickets
import kotlinx.android.synthetic.main.settings.tv_name

class Settings : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        img_home.setOnClickListener {
            finish()
            val intent = Intent(this@Settings, Home::class.java)
            startActivity(intent)
        }

        img_tickets.setOnClickListener {
            finish()
            val intent = Intent(this@Settings, Tickets::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                tv_name.text = p0.child("name").value.toString()
                tv_email.text = p0.child("email").value.toString()
                Picasso.get().load(p0.child("photo").value.toString()).fit().centerCrop().into(img_avatar)
            }

        })

        tv_edit.setOnClickListener {
            val intent = Intent(this@Settings, Edit_Profile::class.java)
            startActivity(intent)
        }

        tv_top_up.setOnClickListener {
            val intent = Intent(this@Settings, Wallet::class.java)
            startActivity(intent)
        }

        tv_logout.setOnClickListener {
            sharedPreferences.edit().putString("username", null).apply()
            finishAffinity()
            val intent = Intent(this@Settings, Splash_Screen::class.java)
            startActivity(intent)
        }

    }
}
