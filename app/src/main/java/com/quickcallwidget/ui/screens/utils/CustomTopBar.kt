package com.quickcallwidget.ui.screens.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.R
import com.quickcallwidget.ui.navigation.Screen

@Composable
fun CustomTopBar(
    navController: NavController,
) {
    Column{
        TextButton(
            onClick = { navController.navigate(Screen.Home.route) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector  = Icons.Default.ArrowBack,
                contentDescription = "Leave App",
                modifier = Modifier.size(34.dp),
            )
            Spacer(modifier = Modifier.width(28.dp))
            Text(
                text = stringResource(id = R.string.first_widget_title),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}