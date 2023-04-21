package com.quickcallwidget.ui.screens

import android.app.Activity
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quickcallwidget.R
import com.quickcallwidget.data.Contact
import com.quickcallwidget.ui.ContactList
import com.quickcallwidget.ui.screens.utils.CustomInfoButton
import com.quickcallwidget.ui.screens.utils.CustomTextField

@Composable

fun MainScreen(
    title: String,
) {
    val phoneNumber = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    val widgetName = sharedPrefs.getString("FirstWidgetName", null)
    val widgetPhone = sharedPrefs.getString("Phone", null)

    val selectedItem by remember { mutableStateOf<Contact?>(Contact("default", "default")) }

    if (widgetName != null) {
        isClicked = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(32.dp))

        selectedItem?.let {
            CustomTextField(
                widgetName = widgetName,
                text = userName,
                label = stringResource(id = R.string.name_max),
                textType = KeyboardType.Text
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        selectedItem?.let {
            CustomTextField(
                widgetName = widgetPhone,
                text = phoneNumber,
                label = stringResource(id = R.string.phone),
                textType = KeyboardType.Phone
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        //share preferences
        val appContext = context.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        Button(
            onClick = {
                if ((userName.value.isNotEmpty()) && (phoneNumber.value.isNotEmpty())) {

                    isClicked = true

                    //alert dialog confirm save contact
                    val addInfoDialog = android.app.AlertDialog.Builder(context)
                        .setMessage(userName.value + "\n${phoneNumber.value}")
                        .setPositiveButton(R.string.accept) { _, _ ->

                            //===save name for first widget share preferences
                            val editor = sharedPrefs.edit()
                            editor.putString("FirstWidgetName", userName.value)
                            editor.putString("Phone", phoneNumber.value)
                            editor.apply()

                            //Add widget to the main screen Alert
                            val mAppWidgetManager = AppWidgetManager.getInstance(context)

                            val myProvider = ComponentName(context, ActionWidgetReceiver::class.java)
                            val b = Bundle()
                            b.putString("123", "ggg")


                            if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
                                val pinnedWidgetCallbackIntent = Intent(context, ActionWidgetReceiver::class.java)
                                val successCallback = PendingIntent.getBroadcast( context,
                                    0,pinnedWidgetCallbackIntent, FLAG_IMMUTABLE)
                                mAppWidgetManager.requestPinAppWidget(myProvider,b,successCallback)
                            }

                            //====
                            val addInfoDialog = android.app.AlertDialog.Builder(context)
                                .setMessage(R.string.message_widget_ready)
                                .setPositiveButton(R.string.ok) { _, _ ->
                                    val activity = (context as? Activity)
                                    activity?.finish()
                                }.create()
                            addInfoDialog.show()
                        }
                        .setNegativeButton(R.string.no) { _, _ ->
                            //stay on same fragment
                            isClicked = false
                        }.create()
                    addInfoDialog.show()
                } else {
                    Toast.makeText(context, R.string.complete, Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            enabled = !isClicked,
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Row {

                if (isClicked) {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Text(
                    text = if (isClicked) stringResource(id = R.string.done) else stringResource(id = R.string.submit),
                    color = if (!isClicked) Color.White else Color.Black,
                    fontSize = 20.sp
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomInfoButton(
                text = stringResource(R.string.edit),
                onClick = {
                    isClicked = false
                    val editor = sharedPrefs.edit()
                    editor.remove("FirstWidgetName")
                    editor.remove("Phone")
                    editor.apply()
                },
                icon = painterResource(id = R.drawable.baseline_edit_note_24)
            )
            CustomInfoButton(
                text = stringResource(id = R.string.info),
                onClick = {
                    val addInfoDialog = android.app.AlertDialog.Builder(context)
                        .setMessage(R.string.how_to_add_widget)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            //stay same plase
                        }.create()
                    addInfoDialog.show()
                },
                icon = painterResource(id = R.drawable.baseline_info_24)
            )
        }

        ContactList(userName) { clickedItem ->

            if (selectedItem != null) {
                // selectedItem = clickedItem
                userName.value = clickedItem.name
                phoneNumber.value = clickedItem.phoneNumber
            }
        }
    }
}
