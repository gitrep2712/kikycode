package com.example.kikyui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kikyui.adapters.CoinAdapter
import com.example.kikyui.adapters.MissedAdapter
import kotlinx.android.synthetic.main.activity_coins.*

class CoinsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coins)
        rvCoin.addItemDecoration(GridSpacingItemDecoration(2, 30, true))

        rvCoin.layoutManager= GridLayoutManager(this,2)
        rvCoin.adapter= CoinAdapter()
    }
}