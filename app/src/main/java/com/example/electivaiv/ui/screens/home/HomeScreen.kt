package com.example.electivaiv.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.screens.login.LoginViewModel
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun HomeScreen(
    openScreen: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var isCleared by remember { mutableStateOf(false) }

    LaunchedEffect(isCleared) {
        if (!viewModel.isSessionActive()) {
            openScreen(ScreensRoutes.LoginScreen.route, ScreensRoutes.HomeScreen.route)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a la pantalla principal")

        Button(onClick = {
            viewModel.clearSharedPreferences()
            isCleared = true
        }) {
            Text(text = "Cerrar sesión")
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    ElectivaIVTheme {
        HomeScreen(
            openScreen = { route, popUp ->}
        )
    }
}