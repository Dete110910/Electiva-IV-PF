package com.example.electivaiv.ui.screens.userProfile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electivaiv.domain.usecase.GetUserInfoByParameterUseCase
import com.example.electivaiv.domain.usecase.GetUserLocalNameUseCase
import com.example.electivaiv.domain.usecase.GetUserProfilePhotoUseCase
import com.example.electivaiv.domain.usecase.SaveUserProfilePhotoUseCase
import com.example.electivaiv.domain.usecase.SetUserProfilePhotoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserProfilePhotoUseCase: GetUserProfilePhotoUseCase,
    private val getUserLocalNameUseCase: GetUserLocalNameUseCase,
    private val getUserInfoByParameterUseCase: GetUserInfoByParameterUseCase,
    private val saveUserProfilePhotoUseCase: SaveUserProfilePhotoUseCase,
    private val setUserProfilePhotoUseCase: SetUserProfilePhotoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> = _uiState.asStateFlow()

    fun getUserProfilePhoto(): String {
        return getUserProfilePhotoUseCase.invoke()
    }

    fun getUserLocalName(): String {
        return getUserLocalNameUseCase.invoke()
    }

    fun getUserInfoByParameter(value: String) {
        viewModelScope.launch {
            getUserInfoByParameterUseCase.invoke(value)
        }
    }

    fun saveProfilePhoto(uri: Uri) {
        viewModelScope.launch {
            val response = saveUserProfilePhotoUseCase.invoke(uri)
            _uiState.value = _uiState.value.copy(
                profilePhoto = response
            )
            Log.d("TEST", "Foto actualizada: $response")
            Log.d("TEST", uiState.toString())
        }
    }

    fun setProfilePhoto(url: String) {
        setUserProfilePhotoUseCase.invoke(url)
    }
}