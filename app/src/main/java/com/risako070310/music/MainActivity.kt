package com.risako070310.music

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userData: SharedPreferences = getSharedPreferences("userId", MODE_PRIVATE)
        val userId = userData.getString("user", "hogehoge")

        db.collection("users").document(userId!!)
            .get()
            .addOnCompleteListener {
                val user = it.result
                if(user != null && user.data != null){
                    nameView.text = user.data?.get("user-name").toString()
                    songView.text = user.data?.get("song").toString()
                    idView.text = userId
                }

            }

    }
}