package com.example.electivaiv.ui.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.Constants.Companion.MINIMUM_PASSWORD_LENGTH_MESSAGE
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.Constants.Companion.VALID_EMAIL_MESSAGE
import com.example.electivaiv.common.ext.isValidEmail
import com.example.electivaiv.common.ext.isValidPassword
import com.example.electivaiv.common.messages.ShowToastComposable
import com.example.electivaiv.domain.usecase.LoginUseCase
import com.example.electivaiv.ui.navigation.ScreensRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    suspend fun login(email: String, password: String): Any? =
        loginUseCase.invoke(email, password)

    fun onLoginInClick(openScreen: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            Log.d(TEST_MESSAGE, VALID_EMAIL_MESSAGE)
            return
        }

        if (!password.isValidPassword()) {
            Log.d(TEST_MESSAGE, MINIMUM_PASSWORD_LENGTH_MESSAGE)
            return
        }

        viewModelScope.launch {
            val result = login(email, password)
            if (result != null) {
                Log.d(TEST_MESSAGE, "Inicio de sesion correcto")
                openScreen(ScreensRoutes.HomeScreen.route, ScreensRoutes.LoginScreen.route)
            } else {
                Log.d(TEST_MESSAGE, "Error logging in")
            }
        }
    }
}