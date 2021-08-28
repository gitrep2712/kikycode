package com.example.kikyui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_connecting.*

class ConnectingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connecting)
        btnNext.setOnClickListener {
            startActivity(Intent(this,VideoCallActivity::class.java))
        }
    }
}