package com.quickcallwidget.ui.screens.widgetTwo

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.R
import com.quickcallwidget.data.Contact
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.ContactList
import com.quickcallwidget.ui.navigation.Screen
import com.quickcallwidget.ui.screens.ActionWidgetReceiverTwo
import com.quickcallwidget.ui.screens.utils.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DetailsScreenTwo(
    navController: NavController,
    myDao: MyDao,
) {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("myPrefsTwo", Context.MODE_PRIVATE)

    val phoneNumber = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    var isClickedTwo by remember { mutableStateOf(false) }

    val widgetName = sharedPrefs.getString("Name", null)
    val widgetPhone = sharedPrefs.getString("Phone", null)

    val receiver = ComponentName(context, ActionWidgetReceiverTwo::class.java)
    val appWidgetManager = AppWidgetManager.getInstance(context)
    val b = Bundle()

    val selectedItem by remember { mutableStateOf<Contact?>(Contact("default", "default")) }

    if (widgetName != null) {
        isClickedTwo = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        CustomTopBar(navController = navController)

        Spacer(modifier = Modifier.height(22.dp))
        Text(text = "Widget two", color = Color.White)
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

        Button(
            onClick = {
                if ((userName.value.isNotEmpty()) && (phoneNumber.value.isNotEmpty())) {

                    // 1. save in share preferences
                    val editor = sharedPrefs.edit()
                    editor
                        .putString("Name", userName.value)
                        .putString("Phone", phoneNumber.value)
                        .putInt("WidNumber", 3)
                    editor.apply()
                    Log.d("VVV", "WidNumber = ${sharedPrefs.getInt("WidNumber",10)}")

                    // 2. save to the room
                    GlobalScope.launch {
                        myDao.insertItem(TestDB(id=2, name=userName.value, phone= phoneNumber.value))
                    }

                    // 3. widget created
                    isClickedTwo = true

                    // 4. add widget to the main screen Alert
                    if (appWidgetManager.isRequestPinAppWidgetSupported) {
                        val pinnedWidgetCallbackIntent =
                            Intent(context, ActionWidgetReceiverTwo::class.java)
                        val successCallback = PendingIntent.getBroadcast(
                            context, 0, pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE
                        )
                        appWidgetManager.requestPinAppWidget(receiver, b, successCallback)
                    }

                    // 6. navigate to the home screen
                    navController.navigate(route = Screen.Home.route)

                } else {
                    Toast.makeText(context, R.string.complete, Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            enabled = !isClickedTwo,
            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Row {

                if (isClickedTwo) {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
                Text(
                    text = if (isClickedTwo) stringResource(id = R.string.done) else stringResource(
                        id = R.string.submit
                    ),
                    color = if (!isClickedTwo) Color.White else Color.Black,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
        //if contact selected not show contact list
        if (!isClickedTwo) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ContactList(userName) { clickedItem ->

                    if (selectedItem != null) {
                        userName.value = clickedItem.name
                        phoneNumber.value = clickedItem.phoneNumber
                    }
                }
            }
        }
    }
}

