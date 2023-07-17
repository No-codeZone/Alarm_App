package com.firebasedemo.alarmapp.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebasedemo.alarmapp.R
import com.firebasedemo.alarmapp.model.WorldTimeItemViewModel
import java.sql.Time

class WorldTimeItemViewAdapter(private val worldList: List<WorldTimeItemViewModel>) :
    RecyclerView.Adapter<WorldTimeItemViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rc_worldclock, parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = worldList[position]
        holder.apply {
//            worldTime.text=itemsViewModel.time
//            worldZone.text=itemsViewModel.zone
            Log.d(TAG, "onBindViewHolder: Adapter Class is here !"+itemsViewModel.time.toString())
        }
    }
    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: Adapter -> "+worldList.size.toString())
        return worldList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var worldTime : View? = ItemView.findViewById(R.id.worldTime)
        var worldZone : View? =ItemView.findViewById(R.id.worldZone)
    }
}

