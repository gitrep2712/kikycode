package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kikyui.adapters.StoryAdapter
import kotlinx.android.synthetic.main.activity_profile_screen.*

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        rvStories.adapter=StoryAdapter()
    }
}