package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kikyui.adapters.MessageAdapter
import kotlinx.android.synthetic.main.activity_message.*

class MessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        supportActionBar?.hide()
        rvMessage.adapter=MessageAdapter()


    }
}