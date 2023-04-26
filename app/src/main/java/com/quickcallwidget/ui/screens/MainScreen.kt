package com.quickcallwidget.ui.screens

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import com.quickcallwidget.R
import com.quickcallwidget.data.Contact
import com.quickcallwidget.data.db.Database
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.ContactList
import com.quickcallwidget.ui.screens.utils.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun MainScreen(
    navController: NavController
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

        //share preferences
        val appContext = context.applicationContext
        val mySharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        //room (save list of widgets)
        val db = Room.databaseBuilder(context, Database::class.java, "new_db").build()
        val dao = db.dao()

        val result by dao.readAll().collectAsState(initial = emptyList())
        Log.d("VVV", "listTest= $result")


        Button(
            onClick = {
                if ((userName.value.isNotEmpty()) && (phoneNumber.value.isNotEmpty())) {

                    isClicked = true

                    //alert dialog confirm save contact
                    val addInfoDialog = android.app.AlertDialog.Builder(context)
                        .setMessage(userName.value + "\n${phoneNumber.value}")
                        .setPositiveButton(R.string.accept) { _, _ ->

                            // 1. ===save name for first widget share preferences
                            val editor = mySharedPrefs.edit()
                            editor.putString("FirstWidgetName", userName.value)
                            editor.putString("Phone", phoneNumber.value)
                            editor.apply()

                            // 2. add widget to the main screen Alert
                            pinWidget(context)

                            // 3. save to db
                            GlobalScope.launch {
                                    dao.insertItem(TestDB(0, userName.value,phoneNumber.value))
                                val result = dao.readAll()
                                Log.d("VVV", "result: $result")
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
                    text = if (isClicked) stringResource(id = R.string.done) else stringResource(
                        id = R.string.submit
                    ),
                    color = if (!isClicked) Color.White else Color.Black,
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

