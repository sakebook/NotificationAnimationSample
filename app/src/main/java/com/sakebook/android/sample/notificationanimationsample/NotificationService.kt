package com.sakebook.android.sample.notificationanimationsample

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NotificationService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val notificationCreator = NotificationCreator()
        startForeground(1000, notificationCreator.create(this, NotificationStyle.MEDIA))
    }
}
