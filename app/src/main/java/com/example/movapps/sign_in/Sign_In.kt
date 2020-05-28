package com.example.movapps.sign_in

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.movapps.Home
import com.example.movapps.R
import com.example.movapps.sign_up.Sign_Up
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign__in.*

class Sign_In : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign__in)

        var mDatabase: DatabaseReference

        btn_register.setOnClickListener {
            val intent = Intent(this@Sign_In, Sign_Up::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            btn_login.isEnabled = false
            btn_register.isEnabled = false
            btn_login.text = "Loading"

            if(et_username.text.toString().isEmpty() || et_password.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Field cannot be blank!", Toast.LENGTH_SHORT).show()
                btn_login.isEnabled = true
                btn_register.isEnabled = true
                btn_login.text = "Masuk"
            } else {
                mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(et_username.text.toString())
                mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error, please try again!", Toast.LENGTH_SHORT).show()

                        btn_login.isEnabled = true
                        btn_register.isEnabled = true
                        btn_login.text = "Masuk"
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()) {
                            var password = p0.child("password").value.toString()

                            if(et_password.text.toString() == password) {
                                val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
                                //bisa tambah shared preferences lain misal nama, email, balance
                                sharedPreferences.edit().putString("username", p0.child("username").value.toString()).apply()
                                sharedPreferences.edit().putString("name", p0.child("name").value.toString()).apply()

                                val intent = Intent(this@Sign_In, Home::class.java)
                                startActivity(intent)
                                finishAffinity()
                            } else {
                                Toast.makeText(applicationContext, "Wrong username or password!", Toast.LENGTH_SHORT).show()
                                btn_login.isEnabled = true
                                btn_register.isEnabled = true
                                btn_login.text = "Masuk"
                            }
                        } else {
                            Toast.makeText(applicationContext, "No username found!", Toast.LENGTH_SHORT).show()
                            btn_login.isEnabled = true
                            btn_register.isEnabled = true
                            btn_login.text = "Masuk"
                        }
                    }
                })
            }
        }
    }
}
