package com.example.medicinediary

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.medicinediary.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)

        setSupportActionBar(binding.toolbar)
        // then setup the action bar, tell it about the DrawerLayout
        setupActionBarWithNavController(navController, binding.drawerLayout)
        drawerLayout = binding.drawerLayout

        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        NavigationUI.setupWithNavController(binding.navView,navController)

        val toolBar = supportActionBar
        toolBar?.setDisplayShowTitleEnabled(false)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController,drawerLayout)
    }
}