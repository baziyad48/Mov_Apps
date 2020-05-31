package com.example.movapps.my_ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movapps.R
import com.example.movapps.model.Ticket
import kotlinx.android.synthetic.main.ticket__details.*

class Ticket_Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ticket__details)

        val data = intent.getParcelableExtra<Ticket>("data")

        tv_title.text = data.title
        tv_date.text = data.date
        tv_location.text = data.location
        tv_seat.text = data.seat
        price_seat.text = data.seat_price
        tv_promo.text = data.promo
        price_promo.text = data.promo_price
        tv_total.text = data.total
        tv_code.text = data.code

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_refund.setOnClickListener {
            Toast.makeText(applicationContext, "Fitur ini belum tersedia!", Toast.LENGTH_LONG).show()
        }
    }

}
