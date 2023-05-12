package com.quickcallwidget.ui.navigation



sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object DetailsScreenOne : Screen(route = "main_screen")
    object DetailsScreenTwo : Screen(route = "main_screen_two")
    object DetailsScreenThree : Screen(route = "main_screen_three")

}