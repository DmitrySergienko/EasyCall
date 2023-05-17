package com.quickcallwidget.ui.screens.utils

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.data.db.TestDB
import com.quickcallwidget.ui.screens.WidgetItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun DeleteWidget(
    navController: NavController,
    myDao: MyDao,
    list: MutableList<TestDB>,
    wName: String,
    wPhone: String,
    wNumber: Int,
    deletedWidget: Int
){
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

    //1. delete widget from the room
    if (list.isNotEmpty()) {
        //  val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(true) {
            GlobalScope.launch {

                val findItemId = list.find { it.id == deletedWidget}
                if (findItemId != null) {
                    myDao.deleteItem(findItemId)
                }
/*
                Log.d("VVV", " findByName = $findItemId")
                Log.d("VVV", " first = ${list.first()}")
                Log.d("VVV", " last = ${list.last()}")
                Log.d("VVV", " list = $list")*/
            }
        }
        // 2. clean share preferences
        val editor = sharedPrefs.edit()
        editor.putString(wName, null)
            .putString(wPhone, null)
            .putInt("WidNumber", wNumber)
            .putInt("WidgetDeleted", 0)
        editor.apply()
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 20.dp),
    )
    {
        items(list) { contact ->
            WidgetItem(
                contact = contact,
                navController = navController
            )
        }
    }
}
