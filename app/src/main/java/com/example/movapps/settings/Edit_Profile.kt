package com.example.movapps.settings

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
import com.example.movapps.R
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.edit__profile.*
import kotlinx.android.synthetic.main.edit__profile.btn_back
import kotlinx.android.synthetic.main.edit__profile.btn_next
import kotlinx.android.synthetic.main.edit__profile.et_email
import kotlinx.android.synthetic.main.edit__profile.et_password
import kotlinx.android.synthetic.main.edit__profile.et_username

class Edit_Profile : AppCompatActivity() {

    lateinit var img_location: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit__profile)

        var mDatabase: DatabaseReference
        var mStorage: StorageReference
        val sharedPreferences = getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        btn_add.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                et_username.setText(p0.child("username").value.toString())
                Picasso.get().load(p0.child("photo").value.toString()).fit().centerCrop().into(img_avatar)
            }
        })

        btn_next.setOnClickListener {
            btn_next.isEnabled = false
            btn_next.text = "Loading"

            if(et_username.text.toString().isEmpty() || et_password.text.toString().isEmpty()
                || et_nama.text.toString().isEmpty() || et_email.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Field cannot be blank!", Toast.LENGTH_SHORT).show()

                btn_next.isEnabled = true
                btn_next.text = "Simpan"
            } else {
                val builder = StringBuilder()
                builder.append(System.currentTimeMillis()).append(".").append(getFileExtension(img_location))
                Log.v("avatar", builder.toString())

                mStorage = FirebaseStorage.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString()).child(builder.toString())
                mStorage.putFile(img_location).addOnSuccessListener {
                    mStorage.downloadUrl.addOnSuccessListener {
                        Log.v("photo_url", it.toString())
                        mDatabase.ref.child("photo").setValue(it.toString())
                        mDatabase.ref.child("email").setValue(et_email.text.toString())
                        mDatabase.ref.child("name").setValue(et_nama.text.toString())
                        mDatabase.ref.child("password").setValue(et_password.text.toString())
                    }
                }.addOnCompleteListener {
                    Toast.makeText(applicationContext, "Your profile will update soon!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Edit_Profile, Settings::class.java)
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

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            img_location = data.data!!
            Picasso.get().load(img_location).centerCrop().fit().into(img_avatar)
        }

        btn_next.visibility = View.VISIBLE
    }
}
