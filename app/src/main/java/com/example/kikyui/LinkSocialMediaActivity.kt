package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_link_social_media.*

class LinkSocialMediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link_social_media)
        btnBack.setOnClickListener {
            finish()
        }
    }
}