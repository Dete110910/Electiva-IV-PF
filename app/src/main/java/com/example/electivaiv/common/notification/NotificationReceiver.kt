package com.example.electivaiv.common.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("NOTIFICATION_MESSAGE")
        message?.let {
            ToastUtil.showToast(context!!, it)
        }
    }
}