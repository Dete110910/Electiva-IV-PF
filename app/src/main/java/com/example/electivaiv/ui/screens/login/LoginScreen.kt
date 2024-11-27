package com.example.electivaiv.ui.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.electivaiv.common.composable.BasicButton
import com.example.electivaiv.common.composable.EmailField
import com.example.electivaiv.common.composable.PasswordField
import com.example.electivaiv.common.composable.TextTitle
import com.example.electivaiv.common.ext.basicButton
import com.example.electivaiv.common.ext.fieldModifier
import com.example.electivaiv.ui.theme.ElectivaIVTheme
import com.example.electivaiv.common.ext.textTitleModifier
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.R.string as AppText


@Composable
fun LoginScreen(
    openScreen: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    onAuthenticatedChange: (Boolean) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    LaunchedEffect(Unit) {
        viewModel.getSessionLocalActive { route ->
            openScreen(route, ScreensRoutes.LoginScreen.route)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle("Ratattouille", Modifier.textTitleModifier().align(Alignment.CenterHorizontally))
        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
        PasswordField(AppText.password, uiState.password, viewModel::onPasswordChange, fieldModifier)
        BasicButton(AppText.login, Modifier.basicButton()) {
            viewModel.onLoginInClick(openScreen, onAuthenticatedChange)
        }
        BasicButton(AppText.register, Modifier.basicButton()) {
            openScreen(ScreensRoutes.SignUpScreen.route, ScreensRoutes.LoginScreen.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    val viewModel: LoginViewModel = hiltViewModel()
    val s = "a"
    ElectivaIVTheme {
        LoginScreen(
            openScreen = {route, popUp -> },
            onAuthenticatedChange = {true}
        )
    }
}