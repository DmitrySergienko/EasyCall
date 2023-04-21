package com.quickcallwidget.ui.screens.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun CustomInfoButton(
    text: String,
    onClick: () -> Unit,
    icon: Painter,

){
    TextButton(
       onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,

        )
    ) {
        Icon(
            painter = icon,
            contentDescription = "UV_button_details",
            modifier = Modifier.size(34.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Light,
            style = TextStyle(Color.White),

        )
    }
}