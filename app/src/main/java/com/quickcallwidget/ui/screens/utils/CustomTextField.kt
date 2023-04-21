package com.quickcallwidget.ui.screens.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp


@Composable
fun CustomTextField(
    widgetName:String?,
    text: MutableState<String>,
    label: String,
    textType: KeyboardType
){

    OutlinedTextField(

        value = text.value,
        onValueChange = {text.value = it.take(15) },
        placeholder = {
            if (widgetName != null) {
                Text(widgetName, color = Color.White)
            }
        },
        label = {
            if (widgetName != null) {
                Text(
                    widgetName, fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                )
            } else {
                Text(
                    label,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Normal,
                )
            }
        },

        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = MaterialTheme.colors.primary,
            fontSize = 20.sp
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = textType
        ),

    )
}
