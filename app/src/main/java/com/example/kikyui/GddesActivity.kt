package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kikyui.ui.home.ExploreFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_gddes.*

class GddesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gddes)
//        val viewPager =OriginViewPager()
//

        pager.adapter = OriginViewPager(supportFragmentManager, lifecycle)

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = "English"
        }.attach()

    }
}