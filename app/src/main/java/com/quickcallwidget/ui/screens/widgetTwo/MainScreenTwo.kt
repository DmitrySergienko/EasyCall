package com.quickcallwidget.ui.screens.widgetTwo

import android.app.PendingIntent
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.R
import com.quickcallwidget.data.Contact
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.ContactList
import com.quickcallwidget.ui.screens.ActionWidgetReceiverTwo
import com.quickcallwidget.ui.screens.utils.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MainScreenTwo(
    navController: NavController,
    myDao: MyDao,
) {
    val phoneNumber = remember { mutableStateOf("") }
    val userName = remember { mutableStateOf("") }
    //val widNumberTwo = remember { mutableStateOf(0) }
    var isClickedTwo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sharedPrefs = context.getSharedPreferences("myPrefsTwo", Context.MODE_PRIVATE)
    val widgetName = sharedPrefs.getString("Name", null)
    val widgetPhone = sharedPrefs.getString("Phone", null)

    val selectedItem by remember { mutableStateOf<Contact?>(Contact("default", "default")) }

    if (widgetName != null) {
        isClickedTwo = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTopBar( navController = navController)

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

        Button(
            onClick = {
                if ((userName.value.isNotEmpty()) && (phoneNumber.value.isNotEmpty())) {

                    isClickedTwo = true

                    //alert dialog confirm save contact
                    // 1. ===save name for first widget share preferences
                    // widNumber.value = 1
                    val editor = sharedPrefs.edit()
                    editor.putString("Name", userName.value)
                        .putString("Phone", phoneNumber.value)
                        .putInt("WidNumber", 1)
                    editor.apply()

                    // 2. add widget to the main screen Alert
                    val mAppWidgetManager = AppWidgetManager.getInstance(context)

                    val myProvider = ComponentName(context, ActionWidgetReceiverTwo::class.java)
                    val b = Bundle()

                    if (mAppWidgetManager.isRequestPinAppWidgetSupported) {
                        val pinnedWidgetCallbackIntent = Intent(context, ActionWidgetReceiverTwo::class.java)
                        val successCallback = PendingIntent.getBroadcast( context,
                            0,pinnedWidgetCallbackIntent, PendingIntent.FLAG_IMMUTABLE
                        )
                        mAppWidgetManager.requestPinAppWidget(myProvider,b,successCallback)

                    }

                    // 3. save to db
                    GlobalScope.launch {
                        myDao.insertItem(TestDB(0, userName.value,phoneNumber.value))
                    }

                    val addInfoDialog = android.app.AlertDialog.Builder(context)
                        .setMessage(userName.value + "\n${phoneNumber.value}")
                        .setPositiveButton(R.string.accept) { _, _ ->
                            isClickedTwo = true
                        }
                        .setNegativeButton(R.string.no) { _, _ ->
                            //stay on same fragment
                            isClickedTwo = false
                        }
                        .create()
                    addInfoDialog.show()

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomInfoButton(

                text = stringResource(R.string.edit),
                onClick = {

                    //1. remove the widget from the share store
                    isClickedTwo = false
                    val editor = sharedPrefs.edit()
                    editor.remove("Name")
                    editor.remove("Phone")
                    editor.remove("WidNumber")
                    editor.apply()

                    //2. remove widget from Home screen
                    //navController.popBackStack()



                },


                icon = painterResource(id = R.drawable.baseline_edit_note_24)
            )
            CustomInfoButton(
                text = stringResource(id = R.string.info),
                onClick = {
                    val addInfoDialog = android.app.AlertDialog.Builder(context)
                        .setMessage(R.string.how_to_add_widget)
                        .setPositiveButton(R.string.ok) { _, _ ->
                            //stay same place
                        }.create()
                    addInfoDialog.show()
                },
                icon = painterResource(id = R.drawable.baseline_info_24)
            )
        }
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

