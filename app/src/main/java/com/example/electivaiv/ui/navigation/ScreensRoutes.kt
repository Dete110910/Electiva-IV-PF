package com.example.electivaiv.ui.navigation

sealed class ScreensRoutes(val route: String) {
    object SignUpScreen : ScreensRoutes("sing_up_routes")
    object LoginScreen : ScreensRoutes("login_routes")
}