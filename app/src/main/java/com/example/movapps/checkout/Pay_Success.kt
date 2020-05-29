package com.example.movapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.movapps.Home
import com.example.movapps.R
import com.example.movapps.Tickets
import kotlinx.android.synthetic.main.pay__success.*

class Pay_Success : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pay__success)

        val top_down: Animation = AnimationUtils.loadAnimation(this,
            R.anim.top_down
        )
        val bottom_up: Animation = AnimationUtils.loadAnimation(this,
            R.anim.bottom_up
        )

        success_image.startAnimation(top_down)
        success_tagline.startAnimation(top_down)
        success_subtitle.startAnimation(top_down)

        btn_lihat.startAnimation(bottom_up)
        btn_home.startAnimation(bottom_up)

        btn_lihat.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@Pay_Success, Tickets::class.java)
            startActivity(intent)
        }

        btn_home.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@Pay_Success, Home::class.java)
            startActivity(intent)
        }
    }
}
