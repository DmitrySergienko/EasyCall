package com.quickcallwidget.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.navigation.Screen
import com.quickcallwidget.ui.screens.utils.fontFamily
import com.quickcallwidget.ui.theme.BlueLight
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun WidgetItem(
    contact: TestDB,
    navController: NavController,
    myDao: MyDao
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
            .clickable {
                //onItemClick(contact)
                navController.navigate(Screen.MainScreen.route)
            },
        backgroundColor = Color.White,
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
                    imageVector = Icons.Default.Person,
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
                        color = Color.Black,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = contact.phone,
                        color = Color.Black,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {

                            val item = TestDB(id = contact.id, name = contact.name, phone = contact.phone)
                            GlobalScope.launch {
                                myDao.deleteItem(item)
                            }
                            navController.navigate(route = Screen.Home.route) //update history screen
                        },
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete button",
                    tint = BlueLight
                )
            }


        }
    }
}

/*
@Composable
@Preview
fun ItemPreview() {
    WidgetItem(navController = rememberNavController())
}*/
