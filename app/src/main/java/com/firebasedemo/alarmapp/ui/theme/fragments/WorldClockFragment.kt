package com.firebasedemo.alarmapp.ui.theme.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebasedemo.alarmapp.databinding.FragmentWorldClockBinding
class WorldClockFragment : Fragment() {
    private lateinit var binding : FragmentWorldClockBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentWorldClockBinding.inflate(inflater,container,false)
        return binding.root
    }
}