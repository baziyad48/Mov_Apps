package com.example.movapps

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movapps.model.Film
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.pay__screen.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class Pay_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay__screen)

        val data = intent.getParcelableExtra<Film>("data")
        val ticket = intent.getStringArrayListExtra("ticket")
        var balance:Int = 0

        tv_title.text = data.title
        var builder = StringBuilder()
        builder.append(SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date())).append(", ").append(SimpleDateFormat("HH.mm", Locale.getDefault()).format(Date())).append(" WIB")
        tv_date.text = builder.toString()

        val random = (0..10).random()
        if(random <=3) {
            tv_location.text = "Movimax Dinoyo"
        } else if(random <= 6) {
            tv_location.text = "Movimax Sarinah"
        } else {
            tv_location.text = "Aurora Cinema"
        }

        builder.clear()
        for(item in ticket){
            builder.append("$item, ")
        }
        builder.setLength(builder.length - 2)
        tv_seat.text = builder.toString()

        tv_promo.text = if(ticket.size > 1) "Buy 1 Get 1" else "No Promo Available"

        price_seat.text = "IDR " + (ticket.size *25) + "K"
        price_promo.text = if(ticket.size > 1) "IDR 25K" else "IDR 0K"
        tv_total.text = if(ticket.size > 1) "IDR " + ((ticket.size *25) - 25) + "K" else "IDR " + (ticket.size *25) + "K"

        var mDatabase: DatabaseReference
        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                balance = p0.child("balance").value.toString().toInt()
                tv_balance.text = "IDR " + balance + "K"
            }

        })

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_pay.setOnClickListener {
            btn_pay.text = "Loading"
            btn_pay.isEnabled = false

            mDatabase = FirebaseDatabase.getInstance().reference.child("Ticket").child(sharedPreferences.getString("username", "").toString()).child(data.title + "_" + System.currentTimeMillis())
            mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.ref.child("id_ticket").setValue(data.title + "_" + System.currentTimeMillis())
                    p0.ref.child("date").setValue(tv_date.text.toString())
                    p0.ref.child("location").setValue(tv_location.text.toString())
                    p0.ref.child("seat").setValue(tv_seat.text.toString())
                    p0.ref.child("price_seat").setValue(price_seat.text.toString())
                    p0.ref.child("promo").setValue(tv_promo.text.toString())
                    p0.ref.child("promo_price").setValue(price_promo.text.toString())
                    p0.ref.child("total").setValue(tv_total.text.toString())
                }

            })

            mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
            mDatabase.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.ref.child("balance").setValue(balance - (ticket.size *25))
                }

            })

            finish()
            val intent = Intent(this@Pay_Screen, Pay_Success::class.java)
            startActivity(intent)
        }

        btn_cancel.setOnClickListener {
            finish()
            val intent = Intent(this@Pay_Screen, Home::class.java)
            startActivity(intent)
        }
    }


}
