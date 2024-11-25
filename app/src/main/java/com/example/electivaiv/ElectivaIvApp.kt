package com.example.electivaiv

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.electivaiv.ui.screens.singup.SingUpScreen
import com.example.electivaiv.ui.theme.ElectivaIVTheme
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.login.LoginScreen
import com.example.electivaiv.ui.screens.main.MainScreen


@Composable
fun ElectivaIvApp() {
    ElectivaIVTheme {
        ChooseNavigation(false)
    }
}

@Composable
fun ChooseNavigation(isUserAuthenticated: Boolean) {
    val navController = rememberNavController()
    if (isUserAuthenticated) {
        MainNavigation(navController)
    } else {
        AuthNavigation(navController)
    }
}

@Composable
fun MainNavigation(navController: NavHostController) {

}

@Composable
fun AuthNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.LoginScreen.route
    ) {
        composable(ScreensRoutes.LoginScreen.route) {

            LoginScreen(
                openScreen = { route, popUp ->
                    navController.navigate(route) {
                            popUpTo(popUp) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreensRoutes.SignUpScreen.route) {
            SingUpScreen(
                openAndPopUp = { route, popUp ->
                    navController.navigate(route) {
                        popUpTo(popUp) { inclusive = true }
                    }
                }
            )
        }

        composable(ScreensRoutes.MainScreen.route) {
            MainScreen()
        }
    }

}