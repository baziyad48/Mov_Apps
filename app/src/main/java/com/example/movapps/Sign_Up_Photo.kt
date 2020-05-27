package com.example.movapps

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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.sign__up__photo.*
import java.lang.StringBuilder

class Sign_Up_Photo : AppCompatActivity() {

    lateinit var img_location: Uri
    val IMG_LIMIT = 1

    lateinit var mDatabase: DatabaseReference
    lateinit var mStorage: StorageReference

    val USERNAME_KEY = "username"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign__up__photo)

        tv_username.text = intent.getStringExtra("username")

        btn_plus.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, IMG_LIMIT)

            btn_save.visibility = View.VISIBLE
        }

        btn_save.setOnClickListener {
            btn_save.text = "Loading"
            btn_save.isEnabled = false
            btn_skip.isEnabled = false

            if(intent.extras == null) {
                Toast.makeText(applicationContext, "Error, please go to previous page!", Toast.LENGTH_SHORT).show()
                btn_save.text = "Simpan"
                btn_save.visibility = View.INVISIBLE

                btn_save.isEnabled = true
                btn_skip.isEnabled = true
            } else {
                               val builder = StringBuilder()
                builder.append(System.currentTimeMillis()).append(".").append(getFileExtension(img_location))
                Log.v("avatar", builder.toString())

                mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(tv_username.text.toString())
                mStorage = FirebaseStorage.getInstance().reference.child("User").child(tv_username.text.toString()).child(builder.toString())

                mStorage.putFile(img_location).addOnSuccessListener {
                    mStorage.downloadUrl.addOnSuccessListener {
                        Log.v("photo_url", it.toString())
                        mDatabase.ref.child("photo").setValue(it.toString())
                    }
                }.addOnCompleteListener {
                    val sharedPreferences = getSharedPreferences(USERNAME_KEY, Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("username", intent.getStringExtra("username")).apply()

                    val intent = Intent(this@Sign_Up_Photo, Home::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
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

        if(requestCode == IMG_LIMIT && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            Picasso.get().load(img_location).centerCrop().fit().into(img_avatar)
        }
    }
}
