package com.easycall.ui.screens.utils

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp


@Composable
fun CustomTextField(
    widgetName:String?,
    name: MutableState<String>,
    lable: String,

){

    OutlinedTextField(
        value = name.value,
        onValueChange = { name.value = it.take(15) },
        placeholder = {
            if (widgetName != null) {
                Text(widgetName, color = Color.White)
            }
        },
        label = {
            if (widgetName != null) {
                Text(
                    widgetName, fontSize = 18.sp,
                    color = Color.White
                )
            } else {
                Text(
                    lable,
                    fontSize = 18.sp,
                    color = Color.White
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
            keyboardType = KeyboardType.Text
        )
    )
}