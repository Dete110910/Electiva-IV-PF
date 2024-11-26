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
import com.example.electivaiv.data.local.SharedPreferences
import com.example.electivaiv.domain.usecase.LoginUseCase
import com.example.electivaiv.ui.navigation.ScreensRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences,
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
                openScreen(ScreensRoutes.MainScreen.route, ScreensRoutes.LoginScreen.route)
            } else {
                Log.d(TEST_MESSAGE, "Error logging in")
            }
        }
    }

    fun getSessionLocalActive(openScreen: (String) -> Unit) {
        val userUid = loginUseCase.getSessionActive()
        val userPath = loginUseCase.getCurrentUserPath()
        if (userUid != null || userPath != null) {
            openScreen(ScreensRoutes.MainScreen.route)
        }
    }

    fun isSessionActive(): Boolean {
    val userUid = sharedPreferences.sharedPreferences.getString("user_uid", null)
    val userPath = sharedPreferences.sharedPreferences.getString("user_path", null)
    return userUid != null && userPath != null
}

    // Funciones para actualizar SharedPreferences
    fun updateUserUid(newUid: String) {
        sharedPreferences.editor.putString("user_uid", newUid).apply()
    }

    fun updateUserPath(newPath: String) {
        sharedPreferences.editor.putString("user_path", newPath).apply()
    }

    // LoginViewModel.kt
    fun clearSharedPreferences() {
        sharedPreferences.editor.clear().apply()
    }
}