package com.saraha.paws.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyFactNotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, MyFactNotificationService::class.java)
        context?.startService(serviceIntent)
    }
}