package com.example.electivaiv.common.composable

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.electivaiv.common.ext.footerModifier
import com.example.electivaiv.ui.navigation.ScreensRoutes

@Composable
fun Footer(
    onNavigate: (String, String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.footerModifier()
    ) {
        val (mainScreen, favoritesScreen, topRestaurants, profileScreen) = createRefs()

        createHorizontalChain(
            mainScreen, favoritesScreen, topRestaurants, profileScreen,
            chainStyle = ChainStyle.Spread
        )
        Button(
            onClick = {
                Log.d("TEST", "Presionando a Home")
                onNavigate(ScreensRoutes.MainScreen.route, ScreensRoutes.MainScreen.route)
            },
            modifier = Modifier.constrainAs(mainScreen) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Icon(Icons.Filled.Home, "Home Button", tint = Color.Black)
        }

        Button(
            onClick = {
                Log.d("TEST", "Presionando a fav")
                onNavigate(ScreensRoutes.UsersWithLikesScreen.route, ScreensRoutes.UsersWithLikesScreen.route)
            },
            modifier = Modifier.constrainAs(favoritesScreen) {
                top.linkTo(mainScreen.top)
                bottom.linkTo(mainScreen.bottom)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Icon(Icons.Filled.Favorite, "Favorites Button", tint = Color.Black)
        }

        Button(
            onClick = {
                onNavigate(ScreensRoutes.TopRestaurantsScreen.route, ScreensRoutes.TopRestaurantsScreen.route)
                Log.d("TEST", "Presionando a top")
            },
            modifier = Modifier.constrainAs(topRestaurants) {
                top.linkTo(mainScreen.top)
                bottom.linkTo(mainScreen.bottom)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Icon(Icons.Filled.Star, "Profile Button", tint = Color.Black)
        }

        Button(
            onClick = {
                //onNavigate(ScreensRoutes.TopRestaurantsScreen.route, ScreensRoutes.TopRestaurantsScreen.route)
                Log.d("TEST", "Presionando a profile")
            },
            modifier = Modifier.constrainAs(profileScreen) {
                top.linkTo(mainScreen.top)
                bottom.linkTo(mainScreen.bottom)
            },
            colors = ButtonDefaults.buttonColors(Color.Transparent)
        ) {
            Icon(Icons.Filled.AccountCircle, "Profile Button", tint = Color.Black)
        }
    }
}