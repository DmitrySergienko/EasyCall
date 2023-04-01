package com.easycall.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.easycall.R
import com.easycall.ui.theme.EasyCallTheme

@Composable
fun CallWidget(

    // onSubmit: (name: String, phoneNumber: String) -> Unit
) {
    val phoneNumber = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    var isClicked by remember { mutableStateOf(false) }
    val context = LocalContext.current



    EasyCallTheme() {
        Image(
            painter = painterResource(R.drawable.ic_background),
            contentDescription = "imageBack",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.7f),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Enter Contact Information",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1,

                )
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    ) {
                        append(name.value)

                    }
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    ) {
                        append(phoneNumber.value)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,)

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it.take(15) },
                label = {
                    Text(
                        "Name (max 15 characters)",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = phoneNumber.value,
                onValueChange = {phoneNumber.value = it.take(15) },
                placeholder = { Text("+7971551234567", color = Color.White) },
                label = {
                    Text(
                        "Phone Number",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            //share preferences
            val appContext = context.applicationContext
            val sharedPrefs = appContext.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val title = sharedPrefs.getString("FirstWidgetName", null)

            if (title != null) {
                Text(title)
            }

            Button(
                onClick = {
                    isClicked = true

                    //===save name for first widget share preferences
                    val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.putString("FirstWidgetName", name.value)
                    editor.apply()
                    //====
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

        }
    }
}