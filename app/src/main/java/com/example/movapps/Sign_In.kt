package com.example.movapps

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign__in.*
import java.lang.ref.Reference

class Sign_In : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference
    val USERNAME_KEY = "username"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign__in)

        btn_register.setOnClickListener {
            val intent = Intent(this@Sign_In, Sign_Up::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            btn_login.isEnabled = false
            btn_login.text = "Loading"

            if(et_username.text.toString().isNullOrEmpty() || et_password.text.toString().isNullOrEmpty()){
                Toast.makeText(applicationContext, "Field cannot be blank!", Toast.LENGTH_SHORT).show()
                btn_login.isEnabled = true
                btn_login.text = "Masuk"
            } else {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(et_username.text.toString())
                mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error, please try again!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()) {
                            var password = p0.child("password").getValue().toString()
                            if(et_password.text.toString() == password) {
                                val sharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                                sharedPreferences.edit().putString("username", et_username.text.toString()).apply()

                                val intent = Intent(this@Sign_In, Home::class.java)
                                startActivity(intent)
                                finishAffinity()
                            } else {
                                Toast.makeText(applicationContext, "Wrong username or password!", Toast.LENGTH_SHORT).show()
                                btn_login.isEnabled = true
                                btn_login.text = "Masuk"
                            }
                        } else {
                            Toast.makeText(applicationContext, "No username found!", Toast.LENGTH_SHORT).show()
                            btn_login.isEnabled = true
                            btn_login.text = "Masuk"
                        }
                    }

                })


            }
        }
    }
}
