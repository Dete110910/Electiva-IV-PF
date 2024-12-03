package com.example.electivaiv.common.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

class NotificationScheduler {

    fun scheduleNotification(
        context: Context,
        notificationId: Int,
        notificationTime: Long,
        message: String
    ) {
        val intent = Intent(context.applicationContext, NotificationReceiver::class.java).apply {
            putExtra("NOTIFICATION_MESSAGE", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            notificationTime,
            pendingIntent
        )

        ToastUtil.showToast(context, "Notification scheduled")
    }
}