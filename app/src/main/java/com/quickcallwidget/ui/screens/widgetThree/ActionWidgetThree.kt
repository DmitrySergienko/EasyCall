package com.quickcallwidget.ui.screens.widgetThree


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.padding
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider


internal val actionWidgetKeyThree = ActionParameters.Key<String>("action-widget-key-Three")

class ActionWidgetThree : GlanceAppWidget() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val appContext = context.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val widgetName = sharedPrefs.getString("NameThree", null)
        Column(
            modifier = GlanceModifier
                .padding(8.dp)

        ) {

            Row() {
                if (widgetName != null) {
                    Button(
                        modifier = GlanceModifier
                            .background(Color.Transparent),
                        text = widgetName,
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 24.sp,
                        ),

                        onClick = actionRunCallback<LogActionCallbackThree>(
                            parameters = actionParametersOf(actionWidgetKeyThree to "log event")
                        ),
                    )
                }
            }
        }
    }
}

class LogActionCallbackThree : ActionCallback {
    override suspend fun onRun(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        val appContext = context.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val phoneNumber = sharedPrefs.getString("PhoneThree", null)

        if (phoneNumber != null) {
            makeCall(context, phoneNumber)
        }
    }

    private fun makeCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        ContextCompat.startActivity(context, intent, null)
    }


}

class ActionWidgetReceiverThree : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ActionWidgetThree()

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)

        val sharedPrefs = context?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs?.edit()
        editor?.putInt("WidgetDeleted", 30)
        editor?.apply()
    }
}