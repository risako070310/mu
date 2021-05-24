package com.risako070310.music

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userData: SharedPreferences = getSharedPreferences("userId", MODE_PRIVATE)

        val user = hashMapOf(
            "user-name" to "とぽ",
            "song" to "はしりがき",
            "artist" to "マカロニえんぴつ",
            "message" to "hogehoge",
            "songURL" to "https://open.spotify.com/track/7CVJWpredjz2mIM9crW1Xo?si=e9a67d45721a4d9b"
        )

        db.collection("users")
            .add(user)
            .addOnSuccessListener { document ->
                val editor = userData.edit()
                editor.putString("user", document.id)
                editor.apply()
                Toast.makeText(this,"成功", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "失敗", Toast.LENGTH_SHORT).show()
            }
    }
}