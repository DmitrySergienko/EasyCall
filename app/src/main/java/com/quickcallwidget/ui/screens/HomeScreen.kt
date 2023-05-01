package com.quickcallwidget.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.R
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.navigation.Screen
import com.quickcallwidget.ui.screens.utils.CycleButtonWithPlus
import com.quickcallwidget.ui.screens.utils.fontFamily
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    myDao: MyDao
) {

    //get instance room bd as injection
    val result by myDao.readAll().collectAsState(initial = emptyList())
    val list = mutableListOf<TestDB>()
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

    for (i in result) list.add(TestDB(i.id, i.name, i.phone))

    MaterialTheme {

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
                .fillMaxSize()
                .padding(10.dp, top = 16.dp)
        ) {
            Text(

                text = stringResource(id = R.string.widget_list),
                color = Color.White,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black
            )

            Column(
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //in case user deleted the widget manually
                if (sharedPrefs.getString("WidgetOneDeleted", "") == "WidgetOneDeleted") {

                    //1. delete widget from the room
                    if(list.isNotEmpty()){
                        val item = list.first()
                        LaunchedEffect(true) {
                            GlobalScope.launch {
                                myDao.deleteItem(item)
                            }
                        }
                        // 2. clean share preferences
                        val editor = sharedPrefs.edit()
                        editor.putString("Name", null)
                            .putString("Phone", null)
                            .putInt("WidNumber", 0)
                        editor.apply()
                    }

                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                    )
                    {
                        items(list) { contact ->
                            WidgetItem(
                                contact = contact,
                                navController = navController,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                    )
                    {
                        items(list) { contact ->
                            WidgetItem(
                                contact = contact,
                                navController = navController,
                            )
                        }
                    }
                }

                CycleButtonWithPlus {

                    val widNumber = sharedPrefs.getInt("WidNumber", 0)

                    when (widNumber) {
                        1 -> navController.navigate(Screen.MainScreenTwo.route)
                        else -> {
                            navController.navigate(Screen.MainScreen.route)
                        }
                    }
                }
            }
        }
    }
}

