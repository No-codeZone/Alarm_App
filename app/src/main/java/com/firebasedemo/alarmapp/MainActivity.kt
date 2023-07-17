package com.firebasedemo.alarmapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import com.firebasedemo.alarmapp.ui.theme.screen.BottomNavDrawerActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent(this, BottomNavDrawerActivity::class.java)
            startActivity(intent)
        },1500)
    }
}
