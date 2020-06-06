package com.example.medicinediary

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

private const val SPLASH_DISPLAY_TIME = 3100L


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        getSupportActionBar()?.hide();

        Handler().postDelayed(Runnable {
            val myIntent = Intent(applicationContext, MainActivity::class.java)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            startActivity(myIntent)
            finish()
        }, SPLASH_DISPLAY_TIME)
    }
}