package com.example.electivaiv

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.addComment.AddCommentScreen
import com.example.electivaiv.ui.screens.authorCommentProfile.AuthorCommentProfileScreen
import com.example.electivaiv.ui.screens.commentDetail.CommentDetailScreen
import com.example.electivaiv.ui.screens.login.LoginScreen
import com.example.electivaiv.ui.screens.login.LoginViewModel
import com.example.electivaiv.ui.screens.main.HomeScreen
import com.example.electivaiv.ui.screens.singup.SingUpScreen
import com.example.electivaiv.ui.screens.topRestaurants.TopRestaurantsScreen
import com.example.electivaiv.ui.screens.userProfile.UserProfileScreen
import com.example.electivaiv.ui.theme.ElectivaIVTheme
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder


@Composable
fun ElectivaIvApp(
    loginViewModel: LoginViewModel = hiltViewModel()

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
    val currentDestination by navController.currentBackStackEntryAsState()
    NavHost(
        navController = navController,
        startDestination = ScreensRoutes.MainScreen.route
    ) {
        composable(ScreensRoutes.MainScreen.route) {
            HomeScreen(
                onAddComment = { route ->
                    navController.navigate(route)
                },
                onShowUserCommentProfile = { route, comment ->
                    val jsonObject = Json.encodeToString(comment)
                    val encodedComment = URLEncoder.encode(jsonObject, "UTF-8")
                    navController.navigate("${route}/$encodedComment")
                },
                onShowCommentDetail = { route, comment ->
                    val jsonObject = Json.encodeToString(comment)
                    val encodedComment = URLEncoder.encode(jsonObject, "UTF-8")
                    navController.navigate("${route}/$encodedComment")
                },
                onNavigate = { route, popUp ->
                    if (currentDestination?.destination?.route != route) {
                        navController.navigate(route)
                    } else {
                        Log.d("TEST", "Estás navegando al mismo lado")
                    }
                }
            )
        }

        composable(ScreensRoutes.AddCommentScreen.route) {
            AddCommentScreen(
                onCloseUi = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "${ScreensRoutes.AuthorCommentProfile.route}/{comment}",
            arguments = listOf(navArgument("comment") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val encodedComment = navBackStackEntry.arguments?.getString("comment")
            encodedComment?.let {
                val decodedComment = URLDecoder.decode(it, "UTF-8")
                Json.decodeFromString<PostComment>(decodedComment)
            }?.let { comment ->
                AuthorCommentProfileScreen(comment = comment,
                    onNavigate = { route, popUp ->
                        navController.navigate(route) {
                            popUpTo(popUp) { inclusive = true }
                        }
                    })
            }
        }

        composable(ScreensRoutes.UserProfileScreen.route) {
            UserProfileScreen(
                onNavigate = { route, popUp ->
                    if (currentDestination?.destination?.route != route) {
                        navController.navigate(route)
                    } else {
                        Log.d("TEST", "Estás navegando al mismo lado")
                    }
                }
            )
        }

        composable(
            route = "${ScreensRoutes.CommentDetail.route}/{comment}",
            arguments = listOf(navArgument("comment") { type = NavType.StringType })
        ) { navBackStackEntry ->
            val encodedComment = navBackStackEntry.arguments?.getString("comment")
            encodedComment?.let {
                val decodedComment = URLDecoder.decode(it, "UTF-8")
                Json.decodeFromString<PostComment>(decodedComment)
            }?.let { comment ->
                CommentDetailScreen(
                    comment = comment,
                    onNavigate = { route, popUp ->
                        navController.navigate(route) {
                            popUpTo(popUp) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(ScreensRoutes.TopRestaurantsScreen.route) {

            TopRestaurantsScreen(
                onNavigate = { route, popUp ->
                    if (currentDestination?.destination?.route != route) {
                        navController.navigate(route) {
                            popUpTo(popUp) { inclusive = true }
                        }
                    } else {
                        Log.d("TEST", "Estás navegando al mismo lado")
                    }
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