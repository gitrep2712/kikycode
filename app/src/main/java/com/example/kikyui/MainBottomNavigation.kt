package com.example.kikyui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kikyui.databinding.ActivityMainBottomNavigationBinding
import com.example.kikyui.fragment.GenderBottomSheet

class MainBottomNavigation : AppCompatActivity(),GenderBottomSheet.ItemClickListener {

    private lateinit var binding: ActivityMainBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView

        navView.itemIconTintList = null;


        val navController =
            findNavController(R.id.nav_host_fragment_activity_main_bottom_navigation)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_explore, R.id.navigation_discover, R.id.navigation_message,R.id.navigation_friend
            )
        )
        supportActionBar?.hide()
        actionBar?.hide()
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onItemClick(item: String?) {
    }

}