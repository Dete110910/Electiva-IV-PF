package com.example.electivaiv.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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
import com.example.electivaiv.R.string as AppText


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle("Ratattouille", Modifier.textTitleModifier().align(Alignment.CenterHorizontally))
        EmailField(uiState.email, viewModel::onEmailChange, fieldModifier)
        PasswordField(uiState.password, viewModel::onPasswordChange, fieldModifier)
        BasicButton(AppText.login, Modifier.basicButton()) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    val viewModel: LoginViewModel = hiltViewModel()
    ElectivaIVTheme {
        LoginScreen(
            modifier = Modifier,
            viewModel
        )
    }
}