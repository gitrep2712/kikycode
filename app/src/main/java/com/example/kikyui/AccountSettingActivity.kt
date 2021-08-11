package com.example.kikyui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_account_setting.*

class AccountSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        llSetting.setOnClickListener {
            startActivity(Intent(this,SettingActivity::class.java))
        }
        llLinkSocial.setOnClickListener {
            startActivity(Intent(this, LinkSocialMediaActivity::class.java))
        }
    }
}