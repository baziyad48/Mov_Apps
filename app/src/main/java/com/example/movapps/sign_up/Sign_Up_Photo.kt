package com.example.movapps.sign_up

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import com.example.movapps.Home
import com.example.movapps.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sign__up__photo.*
import java.lang.StringBuilder

class Sign_Up_Photo : AppCompatActivity() {

    lateinit var img_location: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign__up__photo)

        tv_name.text = intent.getStringExtra("name")
        var mStorage: StorageReference
        var mDatabase: DatabaseReference

        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        val intent = Intent(this@Sign_Up_Photo, Home::class.java)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_plus.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        btn_save.setOnClickListener {
            btn_save.text = "Loading"
            btn_save.isEnabled = false
            btn_skip.isEnabled = false

            if (intent.extras == null) {
                Toast.makeText(applicationContext,"Error, please go to previous page!", Toast.LENGTH_SHORT).show()
                btn_save.text = "Simpan"
                btn_save.visibility = View.INVISIBLE

                btn_save.isEnabled = true
                btn_skip.isEnabled = true
            } else {
                val builder = StringBuilder()
                builder.append(System.currentTimeMillis()).append(".").append(getFileExtension(img_location))
                Log.v("avatar", builder.toString())

                mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(intent.getStringExtra("username"))
                mStorage = FirebaseStorage.getInstance().reference.child("User").child(intent.getStringExtra("username")).child(builder.toString())

                mStorage.putFile(img_location).addOnSuccessListener {
                    mStorage.downloadUrl.addOnSuccessListener {
                        Log.v("photo_url", it.toString())
                        mDatabase.ref.child("photo").setValue(it.toString())
                        mDatabase.ref.child("balance").setValue(500)
                        mDatabase.ref.child("email").setValue(intent.getStringExtra("email"))
                        mDatabase.ref.child("name").setValue(intent.getStringExtra("name"))
                        mDatabase.ref.child("password").setValue(intent.getStringExtra("password"))
                        mDatabase.ref.child("username").setValue(intent.getStringExtra("username"))
                    }
                }.addOnCompleteListener {
                    sharedPreferences.edit().putString("username", intent.getStringExtra("username")).apply()
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }

        btn_skip.setOnClickListener {
            btn_skip.text = "Loading"
            btn_skip.isEnabled = false

            if (intent.extras == null) {
                Toast.makeText(applicationContext,"Error, please go to previous page!", Toast.LENGTH_SHORT).show()
                btn_skip.isEnabled = true
                btn_skip.text = "Lewati"
            } else {
                mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(intent.getStringExtra("username"))
                mDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        Toast.makeText(applicationContext,"Error, please go to previous page!", Toast.LENGTH_SHORT).show()
                        btn_skip.isEnabled = true
                        btn_skip.text = "Lewati"
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        p0.ref.child("balance").setValue(500)
                        p0.ref.child("email").setValue(intent.getStringExtra("email"))
                        p0.ref.child("name").setValue(intent.getStringExtra("name"))
                        p0.ref.child("password").setValue(intent.getStringExtra("password"))
                        p0.ref.child("username").setValue(intent.getStringExtra("username"))

                        sharedPreferences.edit().putString("username", intent.getStringExtra("username")).apply()
                        startActivity(intent)
                        finishAffinity()
                    }
                })
            }
        }
    }

    private fun getFileExtension(imgLocation: Uri): String? {
        val contentResolver = contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imgLocation))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            Picasso.get().load(img_location).centerCrop().fit().into(img_avatar)
        }

        btn_save.visibility = View.VISIBLE
    }
}
