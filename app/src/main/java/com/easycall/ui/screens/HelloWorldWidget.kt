package com.easycall.ui.screens
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.text.Text


class HelloWorldWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val appContext = context.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val title = sharedPrefs.getString("FirstWidgetName", null)

        if (title != null) {
            Text(text = title)
        }
    }

}

class HelloWorldWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = HelloWorldWidget()
}
