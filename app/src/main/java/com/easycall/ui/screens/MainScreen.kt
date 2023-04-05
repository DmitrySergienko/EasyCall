package com.easycall.ui.screens

import android.app.Activity
import android.content.Context
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easycall.R
import com.easycall.ui.ContactList
import com.easycall.ui.screens.utils.CustomInfoButton
import com.easycall.ui.screens.utils.CustomTextField


@Composable

fun MainScreen(
    title: String,
) {
    val phoneNumber = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    val widgetName = sharedPrefs.getString("FirstWidgetName", null)
    val widgetPhone = sharedPrefs.getString("Phone", null)




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

        CustomTextField(widgetName = widgetName, name = name,"Name (max 15 characters)" )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(widgetName = widgetPhone, name = phoneNumber,"Phone Number" )

        Spacer(modifier = Modifier.height(32.dp))

        //share preferences
        val appContext = context.applicationContext
        val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        Button(
            onClick = {
                if ((name.value.isNotEmpty()) && (phoneNumber.value.isNotEmpty())) {

                isClicked = true

                //alert dialog confirm save contact
                val addInfoDialog = android.app.AlertDialog.Builder(context)
                    .setMessage("Confirm save Name: ${name.value}" + "\n" + "Phone: ${phoneNumber.value}")
                    .setPositiveButton(R.string.accept) { _, _ ->

                        //===save name for first widget share preferences
                        val editor = sharedPrefs.edit()
                        editor.putString("FirstWidgetName", name.value)
                        editor.putString("Phone", phoneNumber.value)
                        editor.apply()
                        //====
                        val addInfoDialog = android.app.AlertDialog.Builder(context)
                            .setMessage(R.string.message_widget_ready)
                            .setPositiveButton(R.string.go_to_main_screen) { _, _ ->
                                val activity = (context as? Activity)
                                activity?.finish()
                            }
                            .setNegativeButton(R.string.stay_here) { _, _ ->
                                //stay on same fragment
                            }.create()
                        addInfoDialog.show()
                    }
                    .setNegativeButton(R.string.no) { _, _ ->
                        //stay on same fragment
                        isClicked = false
                    }.create()
                addInfoDialog.show()
            }
                else {
                    Toast.makeText(context, "Complete both fields!", Toast.LENGTH_LONG).show()
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
                    text = if (isClicked) "Done" else "Submit",
                    color = if (!isClicked) Color.White else Color.Black,
                    fontSize = 20.sp
                )

        }
    }
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
        ContactList()

    }
}
