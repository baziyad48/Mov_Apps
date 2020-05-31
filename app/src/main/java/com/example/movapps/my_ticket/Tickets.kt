package com.example.movapps.my_ticket

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movapps.R
import com.example.movapps.settings.Settings
import com.example.movapps.adapter.Adapter_Ticket
import com.example.movapps.checkout.Home
import com.example.movapps.model.Ticket
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.tickets.*

class Tickets : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    private var ticketList = ArrayList<Ticket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tickets)

        img_home.setOnClickListener {
            finish()
            val intent = Intent(this@Tickets, Home::class.java)
            startActivity(intent)
        }

        img_settings.setOnClickListener {
            finish()
            val intent = Intent(this@Tickets, Settings::class.java)
            startActivity(intent)
        }

        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Ticket").child(sharedPreferences.getString("username", "").toString())

        rv_ticket.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                ticketList.clear()

                for (getData in p0.children) {
                    val ticket = getData.getValue(Ticket::class.java)
                    ticketList.add(ticket!!)
                }

                if(ticketList.isNotEmpty()) {
                    rv_ticket.adapter = Adapter_Ticket(ticketList) {
                        val intent = Intent(this@Tickets, Ticket_Details::class.java).putExtra("data", it)
                        startActivity(intent)
                    }
                }

            }

        })

    }
}
