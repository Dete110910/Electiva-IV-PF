package com.example.electivaiv.ui.screens.singup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.ext.isValidPassword
import com.example.electivaiv.common.ext.isValidEmail
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
            Log.d("TEST--", "Enter your name")
            return
        }
        if(uiState.value.lastName.trim() == ""){
            Log.d("TEST--", "Enter your last name")
            return
        }
        if (!email.isValidEmail()) {
            Log.d("TEST--", "Email must be a valid email")
            return
        }

        if (!password.isValidPassword()) {
            Log.d("TEST--", "The password must be at least 6 characters length")
            return
        }

        if (password != uiState.value.confirmPassword) {
            Log.d("TEST--", "Passwords do not match")
            return
        }
        viewModelScope.launch {
            if(signUpUseCase.invoke(email, password)){
                openAndPopUp(ScreensRoutes.LoginScreen.route, ScreensRoutes.SignUpScreen.route)
            }else
                Log.d("TEST--", "Error signing up")
        }
    }
}