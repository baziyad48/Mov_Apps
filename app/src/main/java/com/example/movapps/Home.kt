package com.example.movapps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        val fragment_home = Fragment_Home()
        val fragment_tickets = Fragment_Tickets()
        val fragment_settings = Fragment_Settings()

        setFragment(fragment_home)

        img_home.setOnClickListener {
            setFragment(fragment_home)

            changeIcon(img_home, R.drawable.home_active)
            changeIcon(img_tickets, R.drawable.ticket)
            changeIcon(img_settings, R.drawable.profile)
        }

        img_tickets.setOnClickListener {
            setFragment(fragment_tickets)

            changeIcon(img_home, R.drawable.home)
            changeIcon(img_tickets, R.drawable.ticket_active)
            changeIcon(img_settings, R.drawable.profile)
        }

        img_settings.setOnClickListener {
            setFragment(fragment_settings)

            changeIcon(img_home, R.drawable.home)
            changeIcon(img_tickets, R.drawable.ticket)
            changeIcon(img_settings, R.drawable.profile_active)
        }
    }

    private fun changeIcon(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }
}
