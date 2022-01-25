package com.saraha.paws.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class MyFactNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, MyFactNotificationService::class.java)
        context?.startService(serviceIntent)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            context?.startForegroundService(serviceIntent)
//        } else {
//            context?.startService(serviceIntent)
//        }
    }
}