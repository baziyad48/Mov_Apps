package com.example.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.onboarding__one.*

class Onboarding_One : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding__one)

        val top_down:Animation = AnimationUtils.loadAnimation(this, R.anim.top_down)
        val bottom_up:Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_up)

        ob_image.startAnimation(top_down)
        ob_tagline.startAnimation(top_down)
        ob_subtitle.startAnimation(top_down)

        btn_next.startAnimation(bottom_up)
        btn_skip.startAnimation(bottom_up)

        btn_next.setOnClickListener {
            val intent = Intent(this@Onboarding_One, Onboarding_Two::class.java)
            startActivity(intent)
        }

        btn_skip.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@Onboarding_One, Sign_In::class.java)
            startActivity(intent)
        }
    }
}
