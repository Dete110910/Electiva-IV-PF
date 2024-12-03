package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import javax.inject.Inject

class GetUserProfilePhotoUseCase @Inject constructor(
    private val userServiceSP: UserServiceSP
) {
    fun invoke(): String {
        return userServiceSP.getCurrentUserProfilePhoto() ?: "https://firebasestorage.googleapis.com/v0/b/electiva-iv-593f3.firebasestorage.app/o/img_profile_photo.png?alt=media&token=5ca66a03-b215-4aca-8889-d65499a030db"
    }
}