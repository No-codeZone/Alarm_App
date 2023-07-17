package com.firebasedemo.alarmapp.ui.theme.fragments

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.firebasedemo.alarmapp.databinding.FragmentAlarmBinding
import com.firebasedemo.alarmapp.receiver.AlarmReciever
import java.util.Calendar
import java.util.Locale


class AlarmFragment : Fragment() {
    private lateinit var binding: FragmentAlarmBinding
    val REQUEST_CODE=33

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private fun editAlarmTime() {
        val timePicker: TimePickerDialog
        val currentTime = Calendar.getInstance()
        val hourEdit = currentTime.get(Calendar.HOUR_OF_DAY)
        val minuteEdit = currentTime.get(Calendar.MINUTE)
        timePicker = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourD: Int, min: Int) {
                var hour: Int = hourD % 12
                if (hour == 0) hour = 12
                val _AM_PM = if (hourD >= 12) "PM" else "AM"
                println(String.format(Locale.getDefault(), "%02d:%02d %s", hour, min, _AM_PM))
                currentTime.set(Calendar.HOUR_OF_DAY,hourD)
                currentTime.set(Calendar.MINUTE,min)
                Log.d(
                    TAG,
                    "onTimeSet: Hour&Mins -> $currentTime.set(Calendar.HOUR_OF_DAY,hourD)" +
                            "\t\t\t$currentTime.set(Calendar.MINUTE,min)")
                binding.alarmTime.text = String.format(Locale.getDefault(), "%02d:%02d %s",
                    hour, min, _AM_PM)
                getPref(hour,min,_AM_PM)
                saveTime(hour,min,_AM_PM)
                Log.d(TAG, "onTimeSet editAlarmTime : Time&format : $hour:$min$_AM_PM")
                binding.alarmToggle.setOnClickListener {
                    if(binding.alarmToggle.isChecked)
                    {
                        setAlarm(hour.toLong(),min.toLong())
                        Toast.makeText(context,"Alarm set",Toast.LENGTH_SHORT).show()
                    }
                    else if(!binding.alarmToggle.isChecked){
                        Toast.makeText(context,"Alarm off",Toast.LENGTH_SHORT).show()
                        cancelAlarm()
                    }
                }
            }
        }, hourEdit, minuteEdit, false)
        binding.alarmTime.setOnClickListener {
            binding.alarmToggle.toggle()
            timePicker.show()
        }
    }

    private fun cancelAlarm() {
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, REQUEST_CODE, intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        Log.d(TAG, "cancelAlarm: cancelAlarm->Test2")
        if(pendingIntent!=null){
            alarmManager.cancel(pendingIntent)
        }
//        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
//        val intent = Intent(context, AlarmReciever::class.java)
//        intent.action = "MyBroadcastReceiverAction"
//        val pendingIntent = PendingIntent.getBroadcast(
//            context, REQUEST_CODE, intent,
//            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
//        )
//        alarmManager.cancel(pendingIntent)
    }
    private fun setAlarm(hr : Long,min:Long){
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReciever::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, intent,
            PendingIntent.FLAG_IMMUTABLE)

//        val sharedPreferences:SharedPreferences= activity?.
//        getSharedPreferences("saveTime",Context.MODE_PRIVATE)!!
//        val editor=sharedPreferences.edit()
//        editor.putLong("hour",hr)
//        editor.putLong("min",min)
//        editor.apply()


//        val msUntilTriggerHour: Long = ((hr*3600*1000)+(min*60*1000))
        Log.d(TAG, "setAlarm: msAlarmTrigger : $hr:$min")
        val msUntilTriggerHour: Long = 30000
        Log.d(TAG, "setAlarm: pickerAlarmTrigger : $msUntilTriggerHour")
        val alarmTimeAtUTC: Long = System.currentTimeMillis() + msUntilTriggerHour
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTimeAtUTC,
            pendingIntent)
    }
    @Suppress("NAME_SHADOWING")
    private fun setAlarmPicker() {
        val timePicker: TimePickerDialog
        val currentTime = Calendar.getInstance()
        val hour = currentTime.get(Calendar.HOUR_OF_DAY)
        val minute = currentTime.get(Calendar.MINUTE)
        timePicker = TimePickerDialog(context, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourD: Int, min: Int) {
                var hour : Int = hourD % 12
                if (hour == 0) hour = 12
                val _AM_PM = if (hourD >= 12) "PM" else "AM"
                println(String.format(Locale.getDefault(), "%02d:%02d %s", hour, min, _AM_PM))
                Log.d(TAG, "onTimeSet: setTime "+
                        String.format(Locale.getDefault(),
                            "%02d:%02d %s", hour, min, _AM_PM))
                getPref(hour,min,_AM_PM)
                saveTime(hour,min,_AM_PM)
                Log.d(TAG, "onTimeSet setAlarmPicker: Time&format $hour:$min$_AM_PM")
                binding.alarmTime.text = String.format(Locale.getDefault(), "%02d:%02d %s",
                    hour, min, _AM_PM)
                binding.alarmToggle.setOnClickListener {
                    if(binding.alarmToggle.isChecked)
                    {
                        setAlarm(hour.toLong(),min.toLong())
                        Toast.makeText(context,"Alarm set",Toast.LENGTH_SHORT).show()
                    }
                    else if(!binding.alarmToggle.isChecked){
                        Log.d(TAG, "onTimeSet: cancelAlarm->Test1")
                        cancelAlarm()
                        Toast.makeText(context,"Alarm off",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }, hour, minute, false)
        Log.d(TAG, "setAlarmPicker: hr_min : $hour \t $minute")
        binding.addAlarm.setOnClickListener {
            timePicker.show()
        }
}
    @SuppressLint("SetTextI18n")
    private fun saveTime(hour: Int, min: Int, amPm: String) {
        val sharedPreferences:SharedPreferences=activity?.getSharedPreferences("saveTime",
            Context.MODE_PRIVATE)!!
        val editor=sharedPreferences.edit()
//        val timeText=binding.alarmTime.toString()
//        sharedPreferences.getString(String.format(Locale.getDefault(),"%02d:%02d %s",hour,min,amPm),"")
//        editor.putString(String.format(Locale.getDefault(),"%02d:%02d %s",hour,min,amPm),
//            binding.alarmTime.toString())


        editor.putString(binding.alarmTime.toString(),
            String.format(Locale.getDefault(),"%02d:%02d %s",hour,min,amPm))
//        editor.putInt("hour",hour)
//        editor.putInt("min",min)
//        binding.alarmTime.text=("$hour:$min").toString()
        Log.d(TAG, "saveTime: bindTime : "+binding.alarmTime.toString())
        Log.d(TAG, "saveTime: binding.alarmTime : "+editor.putString("time",
            String.format(Locale.getDefault(),
            "%02d:%02d %s", hour, min, amPm)).toString())
        Log.d(TAG, "saveTime: Time "+String.format(Locale.getDefault(), "%02d:%02d %s",
            hour, min, amPm))
        editor.apply()
    }

    private fun getPref(hour: Int, min: Int, amPm: String) {
        val sharedPreferences:SharedPreferences=activity?.getSharedPreferences("saveTime",
            Context.MODE_PRIVATE)!!
//        sharedPreferences.getInt("hour",0)
//        sharedPreferences.getInt("min",0)
        sharedPreferences.getString(String.format(Locale.getDefault(), "%02d:%02d %s",
            hour, min, amPm),"")
//        Log.d(TAG, "getPref: Time&format -> $hour:$min$amPm")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentAlarmBinding.inflate(inflater, container, false)
        setAlarmPicker()
        editAlarmTime()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        val sharedPreferences:SharedPreferences= activity?.
//        getSharedPreferences("saveTime",Context.MODE_PRIVATE)!!
//        sharedPreferences.getString("hour","")
//        sharedPreferences.getString("min","")
//        sharedPreferences.getString("amPm","")
    }
}
