package com.sakebook.android.sample.notificationanimationsample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        android.util.Log.d(this.javaClass.simpleName, intent.action)


    }
}
