package com.example.movapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.movapps.R
import com.example.movapps.sign_in.Sign_In
import kotlinx.android.synthetic.main.onboarding__three.*

class Onboarding_Three : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding__three)

        val top_down:Animation = AnimationUtils.loadAnimation(this,
            R.anim.top_down
        )
        val bottom_up:Animation = AnimationUtils.loadAnimation(this,
            R.anim.bottom_up
        )

        ob_image.startAnimation(top_down)
        ob_tagline.startAnimation(top_down)
        ob_subtitle.startAnimation(top_down)

        btn_next.startAnimation(bottom_up)

        btn_next.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@Onboarding_Three, Sign_In::class.java)
            startActivity(intent)
        }
    }
}
