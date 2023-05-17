package com.quickcallwidget.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.quickcallwidget.R
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.navigation.Screen
import com.quickcallwidget.ui.screens.utils.CycleButtonWithPlus
import com.quickcallwidget.ui.screens.utils.DeleteWidget
import com.quickcallwidget.ui.screens.utils.fontFamily

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
    val widNumber = sharedPrefs.getInt("WidNumber", 0)
    for (i in result) list.add(TestDB(i.id, i.name, i.phone))


    MaterialTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
        ) {
            Text(
                text = stringResource(id = R.string.widget_list),
                color = Color.White,
                fontFamily = fontFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(42.dp))

            Column(
                modifier = Modifier
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //in case user deleted the widget manually
                if (sharedPrefs.getInt("WidgetDeleted", 0) != 0) {

                    when (sharedPrefs.getInt("WidgetDeleted", 0)) {
                        10 ->{
                            Toast.makeText(context, "Delete widget 1", Toast.LENGTH_SHORT).show()
                            DeleteWidget(
                                navController = navController,
                                myDao = myDao,
                                list = list,
                                wName = "Name",
                                wPhone = "Phone",
                                wNumber = 1,
                                deletedWidget = 1
                            )}
                        20 ->{
                            Toast.makeText(context, "Delete widget 2", Toast.LENGTH_SHORT).show()
                            if (widNumber != 1){
                                DeleteWidget(
                                    navController = navController,
                                    myDao = myDao,
                                    list = list,
                                    wName = "NameTwo",
                                    wPhone = "PhoneTwo",
                                    wNumber = 2,
                                    deletedWidget = 2
                                    )}else{
                                DeleteWidget(
                                    navController = navController,
                                    myDao = myDao,
                                    list = list,
                                    wName = "NameTwo",
                                    wPhone = "PhoneTwo",
                                    wNumber = 1,
                                    deletedWidget = 2
                                    )}
                            }
                        30 ->{
                            when(widNumber){
                                1->{Toast.makeText(context, "Delete widget 3", Toast.LENGTH_SHORT).show()
                                    DeleteWidget(
                                        navController = navController,
                                        myDao = myDao,
                                        list = list,
                                        wName = "NameThree",
                                        wPhone = "PhoneThree",
                                        wNumber = 1,
                                        deletedWidget = 3
                                    )}
                                2->{Toast.makeText(context, "Delete widget 3", Toast.LENGTH_SHORT).show()
                                    DeleteWidget(
                                        navController = navController,
                                        myDao = myDao,
                                        list = list,
                                        wName = "NameThree",
                                        wPhone = "PhoneThree",
                                        wNumber = 2,
                                        deletedWidget = 3
                                    )}
                                3->{Toast.makeText(context, "Delete widget 3", Toast.LENGTH_SHORT).show()
                                    DeleteWidget(
                                        navController = navController,
                                        myDao = myDao,
                                        list = list,
                                        wName = "NameThree",
                                        wPhone = "PhoneThree",
                                        wNumber = 3,
                                        deletedWidget = 3
                                    )}
                            }
                        }
                        else -> {
                            Toast.makeText(context, "No widget available", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    LazyColumn(
                        contentPadding = PaddingValues(bottom = 20.dp),
                    ) {
                        items(list) { contact ->
                            WidgetItem(
                                contact = contact,
                                navController = navController
                            )
                        }
                    }
                }
                if (widNumber != 4) {
                    CycleButtonWithPlus {
                        when (widNumber) {
                            1 -> navController.navigate(Screen.DetailsScreenOne.route)
                            2 -> navController.navigate(Screen.DetailsScreenTwo.route)
                            3 -> navController.navigate(Screen.DetailsScreenThree.route)

                            else -> {
                                navController.navigate(Screen.DetailsScreenOne.route)
                            }
                        }
                    }
                }else{
                    Text(
                        text = stringResource(id = R.string.max),
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.info_remove_widget),
                        color = Color.White,
                        fontFamily = fontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}


