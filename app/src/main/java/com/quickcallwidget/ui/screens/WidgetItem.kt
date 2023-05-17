package com.quickcallwidget.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.screens.utils.fontFamily
import com.quickcallwidget.ui.theme.BlueLight


@Composable
fun WidgetItem(
    contact: TestDB,
    navController: NavController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
            .border(1.dp, color = Color.White, shape = RoundedCornerShape(16.dp))
            .clickable {
                //navController.navigate(Screen.DetailsScreenOne.route)
            },
        backgroundColor = Color.Transparent,

        elevation = 10.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    painter = painterResource(id = com.quickcallwidget.R.drawable.ic_person),
                    contentDescription = "Profile button",
                    tint = BlueLight
                )
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = contact.name,
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.width(100.dp))
                    Text(
                        text = contact.phone,
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }


}

