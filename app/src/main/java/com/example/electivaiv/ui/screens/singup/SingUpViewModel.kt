package com.example.electivaiv.ui.screens.singup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.Constants.Companion.TEST_MESSAGE
import com.example.electivaiv.common.ext.isValidPassword
import com.example.electivaiv.common.ext.isValidEmail
import com.example.electivaiv.domain.model.User
import com.example.electivaiv.domain.usecase.SignUpUseCase
import com.example.electivaiv.ui.navigation.ScreensRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var uiState = mutableStateOf(SingUpUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password
    private val confirmPassword get() = uiState.value.confirmPassword

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onLastNameChange(newValue: String) {
        uiState.value = uiState.value.copy(lastName = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if(uiState.value.name.trim() == ""){
            Log.d(TEST_MESSAGE, "Enter your name")
            return
        }
        if(uiState.value.lastName.trim() == ""){
            Log.d(TEST_MESSAGE, "Enter your last name")
            return
        }
        if (!email.isValidEmail()) {
            Log.d(TEST_MESSAGE, "Email must be a valid email")
            return
        }

        if (!password.isValidPassword()) {
            Log.d(TEST_MESSAGE, "The password must be at least 6 characters length")
            return
        }

        if (password != uiState.value.confirmPassword) {
            Log.d(TEST_MESSAGE, "Passwords do not match")
            return
        }
        val user = User(uiState.value.name,uiState.value.lastName, uiState.value.email, uiState.value.password, "")
        viewModelScope.launch {
            if(signUpUseCase.invoke(user)){
                openAndPopUp(ScreensRoutes.LoginScreen.route, ScreensRoutes.SignUpScreen.route)
            }else
                Log.d(TEST_MESSAGE, "Error signing up")
        }
    }
}