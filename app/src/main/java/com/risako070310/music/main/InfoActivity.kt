package com.risako070310.music.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.risako070310.music.R
import kotlinx.android.synthetic.main.activity_info.*

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        close.setOnClickListener{
            finish()
        }
    }
}