package com.firebasedemo.alarmapp.receiver

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.RingtoneManager
import android.os.Build
import android.os.Build.*
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.*
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.firebasedemo.alarmapp.ui.theme.fragments.AlarmFragment

class AlarmReciever : BroadcastReceiver() {
    val REQUEST_CODE=33
    override fun onReceive(context: Context, intent: Intent) {
        playAudio(context)
        sendNotification(context)
    }

    private fun playAudio(context: Context) {
        val vibrator= context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(4000)
        val audioManager= context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        var alarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        Log.d(TAG, "playAudio: alarmTone1 ")
        if(alarm ==null ){
                alarm=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                Log.d(TAG, "playAudio: alarmTone2 ")
        }else if(audioManager.ringerMode==AudioManager.RINGER_MODE_SILENT || 
            audioManager.ringerMode!=AudioManager.RINGER_MODE_SILENT){
            Log.d(TAG, "playAudio: alarmTone3")
            val ringtone=RingtoneManager.getRingtone(context,alarm)
            ringtone.play()
        }else{
            Log.d(TAG, "playAudio: alarmTone4")
        }
    }
    private fun sendNotification(context: Context) {
        var alarmNotificationManager: NotificationManager? = null
        val NOTIFICATION_CHANNEL_ID = "alarm_app"
        val NOTIFICATION_CHANNEL_NAME = "alarm_app"
        val NOTIFICATION_ID = 1
        val notificationTitle = "Alarm !"
        val notificationContent = "Alarm notification !"
        alarmNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val newIntent = Intent(context, AlarmFragment::class.java)
        val contentIntent = PendingIntent.getActivity(
            context, REQUEST_CODE,
            newIntent, PendingIntent.FLAG_IMMUTABLE
        )
        Log.d(TAG, "sendNotification: $notificationTitle")
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance
        )
        alarmNotificationManager.createNotificationChannel(mChannel)
        val inboxStyle = NotificationCompat.BigTextStyle().bigText(notificationContent)
        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setContentTitle(notificationTitle)
        notificationBuilder.setSmallIcon(android.R.mipmap.sym_def_app_icon)
        notificationBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        notificationBuilder.setContentText(notificationContent)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setStyle(inboxStyle)
        notificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}