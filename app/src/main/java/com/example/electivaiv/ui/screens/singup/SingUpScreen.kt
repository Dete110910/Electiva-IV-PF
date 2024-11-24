package com.example.electivaiv.ui.screens.singup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.composable.BasicButton
import com.example.electivaiv.common.composable.BasicField
import com.example.electivaiv.common.composable.EmailField
import com.example.electivaiv.common.composable.PasswordField
import com.example.electivaiv.common.composable.TextTitle
import com.example.electivaiv.common.ext.*
import com.example.electivaiv.common.ext.textTitleModifier
import com.example.electivaiv.domain.commonViewModel.StorageViewModel
import com.example.electivaiv.ui.navigation.ScreensRoutes
import com.example.electivaiv.ui.theme.ElectivaIVTheme
import com.example.electivaiv.R.string as AppText


@Composable
fun SingUpScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    singUpViewModel: SingUpViewModel = hiltViewModel(),
    storageViewModel: StorageViewModel = hiltViewModel()
) {
    val uiState by singUpViewModel.uiState
    val user by singUpViewModel.userCreated.collectAsState()
    val fieldModifier = Modifier.fieldModifier()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle("Registrarse", Modifier.textTitleModifier().align(Alignment.CenterHorizontally))
        BasicField(AppText.user_name, uiState.name, singUpViewModel::onNameChange, fieldModifier)
        BasicField(AppText.user_last_name, uiState.lastName, singUpViewModel::onLastNameChange, fieldModifier)
        EmailField(uiState.email, singUpViewModel::onEmailChange, fieldModifier)
        PasswordField(AppText.password,uiState.password, singUpViewModel::onPasswordChange, fieldModifier)
        PasswordField(AppText.confirm_password, uiState.confirmPassword, singUpViewModel::onConfirmPasswordChange, fieldModifier)
        BasicButton(AppText.create_account, Modifier.basicButton()){
            singUpViewModel.onSignUpClick(openAndPopUp)
        }
        BasicButton(AppText.cancel, Modifier.basicButton()){
            openAndPopUp(ScreensRoutes.LoginScreen.route, ScreensRoutes.SignUpScreen.route)
        }
        LaunchedEffect(user) {
            user?.let {
                storageViewModel.saveUserData(user!!)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpScreen() {
    ElectivaIVTheme {
        SingUpScreen(
            openAndPopUp = { route, popUp ->}
        )
    }
}