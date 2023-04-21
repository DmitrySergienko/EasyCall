package com.quickcallwidget.ui.screens.utils

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.quickcallwidget.ui.screens.ActionWidgetReceiver

fun pinWidget(context: Context){
    //Pin widget to the main screen Alert
    val mAppWidgetManager = AppWidgetManager.getInstance(context)

    val myProvider = ComponentName(context, ActionWidgetReceiver::class.java)
    val b = Bundle()

    if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
        val pinnedWidgetCallbackIntent = Intent(context, ActionWidgetReceiver::class.java)
        val successCallback = PendingIntent.getBroadcast( context,
            0,pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE
        )
        mAppWidgetManager.requestPinAppWidget(myProvider,b,successCallback)
    }

}