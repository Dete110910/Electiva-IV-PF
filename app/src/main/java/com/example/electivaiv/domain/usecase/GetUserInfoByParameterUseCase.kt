package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.UserService
import javax.inject.Inject

class GetUserInfoByParameterUseCase @Inject constructor(
    private val userService: UserService
) {
    suspend fun invoke(value: String): String {
        return userService. getUserInfoByParameter(value)
    }
}