package com.example.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.settings.*

class Settings : AppCompatActivity() {

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
    }
}
