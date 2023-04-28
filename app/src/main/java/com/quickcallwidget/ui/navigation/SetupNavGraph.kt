package com.quickcallwidget.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quickcallwidget.data.db.MyDao

import com.quickcallwidget.ui.screens.HomeScreen
import com.quickcallwidget.ui.screens.MainScreen
import com.quickcallwidget.ui.screens.widgetTwo.MainScreenTwo


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    myDao: MyDao,

) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                myDao = myDao
            )
        }
        composable(route = Screen.MainScreen.route) {
            MainScreen(
                navController = navController,
                myDao = myDao,
            )
        }
        composable(route = Screen.MainScreenTwo.route) {
            MainScreenTwo(
                navController = navController,
                myDao = myDao,
            )
        }

    }
}