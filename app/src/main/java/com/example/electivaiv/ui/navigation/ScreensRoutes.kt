package com.example.electivaiv.ui.navigation

sealed class ScreensRoutes(val route: String) {
    object SignUpScreen : ScreensRoutes("SignUpScreen")
    object LoginScreen : ScreensRoutes("LoginScreen")
    object MainScreen : ScreensRoutes("MainScreen")
    object AddCommentScreen : ScreensRoutes("AddCommentScreen")
}