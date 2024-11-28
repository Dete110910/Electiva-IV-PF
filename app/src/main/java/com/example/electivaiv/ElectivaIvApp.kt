package com.example.electivaiv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.addComment.AddCommentScreen
import com.example.electivaiv.ui.screens.login.LoginScreen
import com.example.electivaiv.ui.screens.login.LoginViewModel
import com.example.electivaiv.ui.screens.main.HomeScreen
import com.example.electivaiv.ui.screens.singup.SingUpScreen
import com.example.electivaiv.ui.theme.ElectivaIVTheme


@Composable
fun ElectivaIvApp(
    loginViewModel : LoginViewModel = hiltViewModel()

) {
    ElectivaIVTheme {
        var isUserAuthenticated by remember { mutableStateOf(loginViewModel.isSessionActive()) }
        ChooseNavigation(isUserAuthenticated) { newState ->
            isUserAuthenticated = newState
        }
    }
}

@Composable
fun ChooseNavigation(
    isUserAuthenticated: Boolean,
    onAuthenticatedChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()
    if (isUserAuthenticated) {
        MainNavigation(
            navController,
            onAuthenticatedChange
        )
    } else {
        AuthNavigation(
            navController,
            onAuthenticatedChange
        )
    }
}

@Composable
fun MainNavigation(
    navController: NavHostController, onAuthenticatedChange: (Boolean) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.MainScreen.route
    ) {
        composable(ScreensRoutes.MainScreen.route) {
            HomeScreen(
                onAddComment = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(ScreensRoutes.AddCommentScreen.route){
            AddCommentScreen(
                onCloseUi = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun AuthNavigation(
    navController: NavHostController, onAuthenticatedChange: (Boolean) -> Unit
) {

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
                },
                onAuthenticatedChange = { isAuthenticated ->
                    if (isAuthenticated) {
                        onAuthenticatedChange(true)
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
    }

}