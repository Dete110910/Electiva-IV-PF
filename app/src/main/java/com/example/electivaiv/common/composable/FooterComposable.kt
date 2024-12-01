package com.example.electivaiv.common.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
    onNavigate: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier.footerModifier()
    ) {
        val (mainScreen, favoritesScreen, profileScreen) = createRefs()

        createHorizontalChain(
            mainScreen, favoritesScreen, profileScreen,
            chainStyle = ChainStyle.Spread
        )
        Button(
            onClick = {

            },
            modifier = Modifier.constrainAs(mainScreen) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            },
            colors = ButtonDefaults.buttonColors(
                Color.White
            )
        ) {
            Icon(Icons.Filled.Home, "Home Button", tint = Color.Black)
        }

        Button(
            onClick = {
            },
            modifier = Modifier.constrainAs(favoritesScreen) {
                top.linkTo(mainScreen.top)
                bottom.linkTo(mainScreen.bottom)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Gray,

                )
        ) {
            Icon(Icons.Filled.Favorite, "Favorites Button", tint = Color.Black)
        }

        Button(
            onClick = {
                onNavigate(ScreensRoutes.TopRestaurantsScreen.route)
            },
            modifier = Modifier.constrainAs(profileScreen) {
                top.linkTo(mainScreen.top)
                bottom.linkTo(mainScreen.bottom)
            }
        ) {
            Icon(Icons.Filled.AccountCircle, "Profile Button")
        }
    }
}