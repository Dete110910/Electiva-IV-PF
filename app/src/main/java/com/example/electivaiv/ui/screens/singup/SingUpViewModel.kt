package com.example.electivaiv.ui.screens.singup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.common.ext.isValidPassword
import com.example.electivaiv.common.ext.isValidEmail
import com.example.electivaiv.domain.usecase.SignUpUseCase
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
        if (!email.isValidEmail()) {
            Log.d("TEST--", "Email must be a valid email")
            return
        }

        if (!password.isValidPassword()) {
            Log.d("TEST--", "The password must be at least 6 characters length")
            return
        }
        //val confirmPassword = uiState.value.confirmPassword
        if (password == confirmPassword) {

            Log.d("TEST--", "Passwords do not match")
            Log.d("TEST--", "Passwords: ${password}")
            Log.d("TEST--", "Passwords2: ${confirmPassword}")
            return
        }
        viewModelScope.launch {
            signUpUseCase.invoke(email, password)
        }
    }
}