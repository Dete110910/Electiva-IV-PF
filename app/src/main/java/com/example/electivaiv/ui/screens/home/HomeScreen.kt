package com.example.electivaiv.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electivaiv.ui.screens.singup.SingUpScreen
import com.example.electivaiv.ui.theme.ElectivaIVTheme

@Composable
fun HomeScreen(
    openScreen: (String, String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a la pantalla principal")
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