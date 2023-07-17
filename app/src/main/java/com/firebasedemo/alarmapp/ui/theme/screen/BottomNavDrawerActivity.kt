package com.firebasedemo.alarmapp.ui.theme.screen

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.firebasedemo.alarmapp.R
import com.firebasedemo.alarmapp.databinding.ActivityBottomNavDrawerBinding
import com.firebasedemo.alarmapp.ui.theme.fragments.AlarmFragment
import com.firebasedemo.alarmapp.ui.theme.fragments.StopWatchFragment
import com.firebasedemo.alarmapp.ui.theme.fragments.TimerFragment
import com.firebasedemo.alarmapp.ui.theme.fragments.WorldClockFragment

class BottomNavDrawerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavDrawerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_nav_drawer)
        processFragment(AlarmFragment())
        binding.bottomNavigationViewAlarm.setOnItemSelectedListener{
            when(it.itemId){
                R.id.alarm -> {
                    processFragment(AlarmFragment())
                    true
                }
                R.id.stopWatch -> {
                    processFragment(StopWatchFragment())
                    true
                }
                R.id.worldClock ->{
                    processFragment(WorldClockFragment())
                    true
                }
                R.id.timer -> {
                    processFragment(TimerFragment())
                    true
                }
                else -> {
                    Log.e(TAG, "onCreate: BottomNavDrawer -> $binding")
                    true
                }
//                else -> {}
            }
        }
    }
    private fun processFragment(fragment:Fragment){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentBinding,fragment)
        transaction.commitNow()
    }
}

