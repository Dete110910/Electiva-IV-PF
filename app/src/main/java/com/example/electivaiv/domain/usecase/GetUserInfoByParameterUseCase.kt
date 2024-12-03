package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import com.example.electivaiv.data.network.services.UserService
import javax.inject.Inject

class GetUserInfoByParameterUseCase @Inject constructor(
    private val userService: UserService,
    private val userServiceSP: UserServiceSP
) {
    suspend fun invoke(): String {
        return userService.getEmailFromCloud(userServiceSP.getCurrentUserUid()!!)
    }
}