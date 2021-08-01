package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kikyui.adapters.GddessAdapter
import com.example.kikyui.adapters.MissedAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.fragment_gddess.view.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        rvMissed.addItemDecoration(GridSpacingItemDecoration(2, 30, true))

        rvMissed.layoutManager= GridLayoutManager(this,2)
        rvMissed.adapter= MissedAdapter()
    }
}