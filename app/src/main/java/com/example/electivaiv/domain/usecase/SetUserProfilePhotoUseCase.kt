package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import com.example.electivaiv.data.network.services.UserService
import javax.inject.Inject

class SetUserProfilePhotoUseCase @Inject constructor(
    private val userService: UserService,
    private val userServiceSP: UserServiceSP
) {
    fun invoke(url: String) {
        val uid = userServiceSP.getCurrentUserUid() ?: ""
        userService.setProfilePhoto(uid, url)
        userServiceSP.updateCurrentUserProfilePhoto(url)
    }
}