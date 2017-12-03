package com.sakebook.android.sample.notificationanimationsample

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.widget.RemoteViews

/**
 * Created by sakemotoshinya on 2017/12/03.
 */
class NotificationCreator {

    fun create(context: Context, style: NotificationStyle): Notification {
        val builder = NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android)
                .setContentTitle("ContentTitle")
                .setContentText("ContentText")
                .setSubText("SubText")
                .setTicker("Ticker")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setLights(Color.YELLOW, 500, 500)
                .setVibrate(longArrayOf(100, 100, 100))

        return when(style) {
            NotificationStyle.NORMAL          -> createNormalNotification(context, builder)
            NotificationStyle.CUSTOM_CONTENT  -> createCustomContentNotification(context, builder)
            NotificationStyle.CUSTOM_HEADS_UP -> createCustomHeadsUpNotification(context, builder)
            NotificationStyle.MEDIA           -> createMediaNotification(context, builder)
            NotificationStyle.DECORATED_MEDIA -> createDecorateMediaNotification(context, builder)
        }
    }

    private fun createNormalNotification(context: Context, builder: NotificationCompat.Builder): Notification {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = NotificationStyle.NORMAL.name
        }
        builder
                .addAction(R.drawable.ic_android, NotificationStyle.NORMAL.name,
                        PendingIntent.getBroadcast(context,
                                NotificationStyle.NORMAL.ordinal,
                                broadcastIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.hologram))
        return builder.build()
    }

    private fun createCustomContentNotification(context: Context, builder: NotificationCompat.Builder): Notification {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = NotificationStyle.CUSTOM_CONTENT.name
        }
        builder
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(createCustomContentView())
                .setCustomBigContentView(createCustomBigContentView())
                .addAction(R.drawable.ic_android, NotificationStyle.CUSTOM_CONTENT.name,
                        PendingIntent.getBroadcast(context, NotificationStyle.CUSTOM_CONTENT.ordinal, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
        return builder.build()
    }

    private fun createCustomHeadsUpNotification(context: Context, builder: NotificationCompat.Builder): Notification {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = NotificationStyle.CUSTOM_HEADS_UP.name
        }
        builder
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setCustomHeadsUpContentView(createCustomHeadsUpView())
                .addAction(R.drawable.ic_android, NotificationStyle.CUSTOM_HEADS_UP.name,
                        PendingIntent.getBroadcast(context, NotificationStyle.CUSTOM_HEADS_UP.ordinal, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
        return builder.build()
    }

    private fun createMediaNotification(context: Context, builder: NotificationCompat.Builder): Notification {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = NotificationStyle.MEDIA.name
        }
        builder
                .setStyle(android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                )
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setColorized(true)
                .addAction(R.drawable.ic_android, NotificationStyle.MEDIA.name,
                        PendingIntent.getBroadcast(context, NotificationStyle.MEDIA.ordinal, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_star, "10",
                        PendingIntent.getBroadcast(context, 10, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_favorite_border, "20",
                        PendingIntent.getBroadcast(context, 20, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.hologram))
        return builder.build()
    }

    private fun createDecorateMediaNotification(context: Context, builder: NotificationCompat.Builder): Notification {
        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            this.action = NotificationStyle.DECORATED_MEDIA.name
        }
        builder
                .setStyle(android.support.v4.media.app.NotificationCompat.DecoratedMediaCustomViewStyle()
                        .setShowActionsInCompactView(1)
                )
                .setColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                .setColorized(true)
                .setCustomContentView(createCustomContentView())
                .setCustomBigContentView(createCustomMediaBigContentView())
                .setCustomHeadsUpContentView(createCustomHeadsUpView())
                .addAction(R.drawable.ic_android, NotificationStyle.DECORATED_MEDIA.name,
                        PendingIntent.getActivity(context, NotificationStyle.DECORATED_MEDIA.ordinal, Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_star, "10",
                        PendingIntent.getBroadcast(context, 10, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .addAction(R.drawable.ic_favorite_border, "20",
                        PendingIntent.getBroadcast(context, 20, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.hologram))
        return builder.build()
    }

    private fun createCustomContentView(): RemoteViews {
        val remoteView = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_custom_content)
        remoteView.setTextViewText(R.id.text1, "title")
        remoteView.setTextViewText(R.id.text2, "description")
        remoteView.setImageViewResource(R.id.image, R.drawable.hologram)
        return remoteView
    }

    private fun createCustomBigContentView(): RemoteViews {
        val remoteView = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_custom_big_content)
        remoteView.setTextViewText(R.id.text1, "title")
        remoteView.setTextViewText(R.id.text2, "description")
        remoteView.setImageViewResource(R.id.image, R.drawable.hologram)
        return remoteView
    }

    private fun createCustomHeadsUpView(): RemoteViews {
        val remoteView = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_custom_heads_up)
        remoteView.setTextViewText(R.id.text1, "Heads up!!")
        remoteView.setTextViewText(R.id.text2, "Notification")
        return remoteView
    }

    private fun createCustomMediaBigContentView(): RemoteViews {
        val remoteView = RemoteViews(BuildConfig.APPLICATION_ID, R.layout.notification_custom_media_big_content)
        remoteView.setTextViewText(R.id.text1, "Big")
        remoteView.setTextViewText(R.id.text2, "Content")
        remoteView.setTextViewText(R.id.text3, "Notification")
        return remoteView
    }
}