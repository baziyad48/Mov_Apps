package com.example.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.tickets.*

class Tickets : AppCompatActivity() {

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
    }
}
