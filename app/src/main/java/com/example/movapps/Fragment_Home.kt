package com.example.movapps

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment__home.*
import java.lang.StringBuilder

/**
 * A simple [Fragment] subclass.
 */
class Fragment_Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = this.activity!!.getSharedPreferences("MOV_APPS", Context.MODE_PRIVATE)
        var mDatabase: DatabaseReference

        mDatabase = FirebaseDatabase.getInstance().reference.child("User").child(sharedPreferences.getString("username", "").toString())
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity, "Something error, please re-open application!", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                var builder = StringBuilder()
                builder.append("IDR ").append(p0.child("balance").value.toString()).append("K")
                tv_balance.text = builder.toString()
                tv_name.text = p0.child("name").value.toString()

                Picasso.get().load(p0.child("photo").value.toString()).fit().centerCrop().into(img_avatar)

                //masukin data ke recylerview nya
            }
        })
    }
}
