package com.quickcallwidget.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.quickcallwidget.data.db.MyDao
import com.quickcallwidget.ui.screens.DetailsScreenOne

import com.quickcallwidget.ui.screens.HomeScreen
import com.quickcallwidget.ui.screens.widgetThree.DetailsScreenThree
import com.quickcallwidget.ui.screens.widgetTwo.DetailsScreenTwo


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
        composable(route = Screen.DetailsScreenOne.route) {
            DetailsScreenOne(
                navController = navController,
                myDao = myDao,
            )
        }
        composable(route = Screen.DetailsScreenTwo.route) {
            DetailsScreenTwo(
                navController = navController,
                myDao = myDao,
            )
        }
        composable(route = Screen.DetailsScreenThree.route) {
            DetailsScreenThree(
                navController = navController,
                myDao = myDao,
            )
        }

    }
}