package com.risako070310.music.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.risako070310.music.main.MainActivity
import com.risako070310.music.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userData: SharedPreferences = getSharedPreferences("userId", MODE_PRIVATE)
        val userId = userData.getString("user", "hogehoge")

        if (userId != null && userId != "hogehoge") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}