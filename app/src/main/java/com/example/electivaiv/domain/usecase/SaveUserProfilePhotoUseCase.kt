package com.example.electivaiv.domain.usecase

import android.net.Uri
import com.example.electivaiv.data.network.services.UserService
import javax.inject.Inject

class SaveUserProfilePhotoUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend fun invoke (uri: Uri) : String {
        return userService.saveProfilePhoto(uri)
    }
}