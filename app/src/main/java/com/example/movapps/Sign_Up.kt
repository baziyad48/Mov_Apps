package com.example.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.sign__in.*
import kotlinx.android.synthetic.main.sign__up.*
import kotlinx.android.synthetic.main.sign__up.et_password
import kotlinx.android.synthetic.main.sign__up.et_username

class Sign_Up : AppCompatActivity() {

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign__up)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_next.setOnClickListener {
            btn_next.isEnabled = false
            btn_next.text = "Loading"

            if(et_username.text.toString().isNullOrEmpty() || et_password.text.toString().isNullOrEmpty()
                || et_name.text.toString().isNullOrEmpty() || et_email.text.toString().isNullOrEmpty()) {
                Toast.makeText(applicationContext, "Field cannot be blank!", Toast.LENGTH_SHORT).show()

                btn_next.isEnabled = true
                btn_next.text = "Lanjutkan"
            } else {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(et_username.text.toString())
                mDatabase.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext, "Error, please try again!", Toast.LENGTH_SHORT).show()

                        btn_next.isEnabled = true
                        btn_next.text = "Lanjutkan"
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if(p0.exists()) {
                            Toast.makeText(applicationContext, "Username already exist!", Toast.LENGTH_SHORT).show()

                            btn_next.isEnabled = true
                            btn_next.text = "Lanjutkan"
                        } else {
                            val bundle = Bundle()
                            bundle.putString("username", et_username.text.toString())

                            mDatabase.ref.child("balance").setValue(500)
                            mDatabase.ref.child("email").setValue(et_email.text.toString())
                            mDatabase.ref.child("name").setValue(et_name.text.toString())
                            mDatabase.ref.child("password").setValue(et_password.text.toString())
                            mDatabase.ref.child("username").setValue(et_username.text.toString())

                            val intent = Intent(this@Sign_Up, Sign_Up_Photo::class.java)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        }
                    }
                })
            }
        }
    }
}
