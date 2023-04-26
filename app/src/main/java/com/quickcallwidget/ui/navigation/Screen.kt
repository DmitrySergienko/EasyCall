package com.quickcallwidget.ui.navigation



sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object MainScreen : Screen(route = "main_screen")

}