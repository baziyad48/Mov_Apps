package com.example.movapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.movapps.R
import com.example.movapps.model.Film
import kotlinx.android.synthetic.main.chair__selection.*

class Chair_Selection : AppCompatActivity() {

    var status_one:Boolean = false
    var status_two:Boolean = false
    var total:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chair__selection)

        val data = intent.getParcelableExtra<Film>("data")
        var ticket = ArrayList<String>()

        btn_back.setOnClickListener {
            onBackPressed()
        }

        tv_title.text = data.title

        chair_one.setOnClickListener {
            if(status_one){
                chair_one.setImageResource(R.drawable.chair_empty)
                status_one = false
                total--
                beliTiket(total)
                ticket.remove("E4")
            } else {
                chair_one.setImageResource(R.drawable.chair_selected)
                status_one = true
                total++
                beliTiket(total)
                ticket.add("E4")
            }
        }


        chair_two.setOnClickListener {
            if(status_two){
                chair_two.setImageResource(R.drawable.chair_empty)
                status_two = false
                total--
                beliTiket(total)
                ticket.remove("E5")
            } else {
                chair_two.setImageResource(R.drawable.chair_selected)
                status_two = true
                total++
                beliTiket(total)
                ticket.add("E5")
            }
        }

        btn_next.setOnClickListener {
            val intent = Intent(this@Chair_Selection, Pay_Screen::class.java).putExtra("data", data).putExtra("ticket", ticket)
            startActivity(intent)
        }

    }

    private fun beliTiket(total:Int) {
        if(total == 0) {
            btn_next.text = "Beli Tiket"
            btn_next.visibility = View.INVISIBLE
        } else {
            btn_next.setText("Beli Tiket ("+total+")")
            btn_next.visibility = View.VISIBLE
        }
    }
}
