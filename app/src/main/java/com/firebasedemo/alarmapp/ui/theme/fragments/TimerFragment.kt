package com.firebasedemo.alarmapp.ui.theme.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebasedemo.alarmapp.databinding.FragmentTimerBinding
import java.util.Locale

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding
    private var timerRunning : Boolean = false
    private val START_TIME_IN_MILLIS: Long = 600000
    private lateinit var countDownTimer : CountDownTimer
    private var timeLeftInMillis = START_TIME_IN_MILLIS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentTimerBinding.inflate(inflater,container,false)
        val playClicked=binding.play
        val stopBtn=binding.reset
        playClicked.setOnClickListener {
            Toast.makeText(context,"Timer started", Toast.LENGTH_LONG).show()
            binding.play.visibility = ImageButton.GONE
            binding.reset.visibility = ImageButton.VISIBLE
            startStop()
        }
        stopBtn.setOnClickListener {
            Toast.makeText(context,"Timer stopped",Toast.LENGTH_LONG).show()
            binding.reset.visibility = ImageButton.GONE
            binding.play.visibility = ImageButton.VISIBLE
            stopTimer()
        }
        binding.pause.setOnClickListener {
            Toast.makeText(context,"Timer paused",Toast.LENGTH_LONG).show()
            pauseTimer()
        }
        updateTimer()
        return binding.root
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        timerRunning=false
    }

    private fun startStop() {
        if (timerRunning){
            pauseTimer()
        }else{
            startTimer()
        }
    }
    private fun startTimer() {
        countDownTimer=(object : CountDownTimer(timeLeftInMillis, 1000){
            override fun onTick(l: Long) {
                timeLeftInMillis=l
                updateTimer()
            }
            override fun onFinish() {
                Log.d(TAG, "onFinish: startTimer -> $timeLeftInMillis")
                timerRunning=false
            }
        }).start()
        timerRunning=true
    }
    private fun updateTimer(){
        val min : Long =(timeLeftInMillis /1000)/60
        val secs : Long =(timeLeftInMillis / 1000)%60

        Log.d(TAG, "updateTimer: mins&secs -> $min $secs")
            binding.countDownTimer.text= String.format(Locale.getDefault(),"%02d:%02d",min,secs)
        Log.d(TAG, "updateTimer: TimerText -> $binding.countDownTimer.text= " +
                "String.format(Locale.getDefault(),\"%02d:%02d\",min,secs)")
    }

    private fun stopTimer() {
//        countDownTimer.cancel()
//        timerRunning=false
        timeLeftInMillis = START_TIME_IN_MILLIS
        updateTimer()
    }
//    private fun countDownTimer(timer : Long, secs:Long) {
//       fun onTick(millisUntilFinished:Long) {
//           val f: NumberFormat
//           f = DecimalFormat("00")
//           val hour : Long = (millisUntilFinished / 3600000) % 24
//           val min : Long = (millisUntilFinished / 60000) % 60
//           val sec:Long = (millisUntilFinished / 1000) % 60
//           binding.timer.text = (String.format(hour.toString()) + ":" +
//                   String.format(min.toString()) + ":" + String.format(sec.toString()))
//           Toast.makeText(context,"Time ${binding.timer}",Toast.LENGTH_SHORT).show()
//           binding.timer.start()
//        }
//       fun onFinished(){
//           binding.timer.text=("00:00:00")
//           binding.timer.stop()
//       }
//    }
}