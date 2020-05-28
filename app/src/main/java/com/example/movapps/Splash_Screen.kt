package com.example.movapps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.movapps.onboarding.Onboarding_One
import kotlinx.android.synthetic.main.splash_screen.*

class Splash_Screen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash)
        app_logo.startAnimation(animation)

        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        var username_local = sharedPreferences.getString("username", "")

        if(username_local.isNullOrEmpty()){
            var handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@Splash_Screen, Onboarding_One::class.java)
                startActivity(intent)
                finish()
            },2000)
        } else {
            var handler = Handler()
            handler.postDelayed({
                val intent = Intent(this@Splash_Screen, Home::class.java)
                startActivity(intent)
                finish()
            },2000)
        }

    }
}
