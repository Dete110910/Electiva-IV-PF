package com.example.electivaiv.ui.screens.singup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.electivaiv.common.ext.isValidPassword
import com.example.electivaiv.common.ext.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SingUpViewModel @Inject constructor() : ViewModel() {
    var uiState = mutableStateOf(SingUpUiState())
        private set

    private val email get() = uiState.value.email
    private val password get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }

    fun onNameChange(newValue: String){
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onLastNameChange(newValue: String){
        uiState.value = uiState.value.copy(lastName = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit){
        if (!email.isValidEmail()) {
            Log.d("TEST--", "Please, insert a valid email.")
            return
        }

        if (!password.isValidPassword()) {
            Log.d("TEST--", "The password must be at least 6 characters length")
            return
        }

        if (password == uiState.value.confirmPassword) {
            Log.d("TEST--", "Passwords do not match")
            return
        }
    }
}