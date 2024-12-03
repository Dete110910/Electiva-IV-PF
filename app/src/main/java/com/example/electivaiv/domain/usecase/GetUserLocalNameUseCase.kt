package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import javax.inject.Inject

class GetUserLocalNameUseCase @Inject constructor(
    private val userServiceSP: UserServiceSP
) {
    fun invoke(): String {
        return userServiceSP.getCurrentUserName() ?: "No local name"
    }
}