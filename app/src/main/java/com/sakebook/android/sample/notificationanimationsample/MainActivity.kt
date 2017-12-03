package com.sakebook.android.sample.notificationanimationsample

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val notificationManager: NotificationManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
    private val notificationCreator: NotificationCreator = NotificationCreator()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Sample channel", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        setupListeners()
    }

    private fun setupListeners() {
        button_normal.setOnClickListener { showNotification(NotificationStyle.NORMAL) }
        button_custom_content.setOnClickListener { showNotification(NotificationStyle.CUSTOM_CONTENT) }
        button_custom_headsup.setOnClickListener { showNotification(NotificationStyle.CUSTOM_HEADS_UP) }
        button_media.setOnClickListener { showNotification(NotificationStyle.MEDIA) }
        button_media_service.setOnClickListener {
            val intent = Intent(this, NotificationService::class.java)
            startService(intent)
        }
        button_decorate_media.setOnClickListener { showNotification(NotificationStyle.DECORATED_MEDIA) }
    }

    private fun showNotification(style: NotificationStyle) {
        val notification = notificationCreator.create(this, style)
        notificationManager.notify(style.ordinal, notification)
    }

    companion object {
        val CHANNEL_ID = "sample"
    }
}
