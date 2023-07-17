package com.firebasedemo.alarmapp.ui.theme.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebasedemo.alarmapp.databinding.FragmentStopWatchBinding
class StopWatchFragment : Fragment() {
    private lateinit var binding: FragmentStopWatchBinding
    var lastPause : Long = 0
//    var started : Int =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
        private fun startStopWatch(){
        binding.start.setOnClickListener {
            Toast.makeText(context,"Stopwatch started",Toast.LENGTH_LONG).show()
                Log.d(TAG, "onCreate: stopWatchFragment(1) is here $binding")
                binding.stopWatch.base= SystemClock.elapsedRealtime()-lastPause
                Log.d(TAG, "startStopWatch: startStopWatch ${binding.stopWatch.base}")
                binding.stopWatch.start()
        }
    }
    private fun resetStopWatch(){
        binding.reset.setOnClickListener {
            Toast.makeText(context,"Stopwatch stopped",Toast.LENGTH_LONG).show()
            binding.stopWatch.base=SystemClock.elapsedRealtime()
        }
    }
    private fun pauseStopWatch() {
        binding.pause.setOnClickListener {
            Toast.makeText(context,"Stopwatch paused",Toast.LENGTH_LONG).show()
            lastPause=SystemClock.elapsedRealtime()-binding.stopWatch.base
            binding.stopWatch.stop()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentStopWatchBinding.inflate(inflater,container,false)
        startStopWatch()
        resetStopWatch()
        pauseStopWatch()
        return binding.root
    }
}
