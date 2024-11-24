package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.AuthenticationService
import com.example.electivaiv.domain.model.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend fun invoke(email: String, password: String): String? {
        val uid = authenticationService.signUp(email, password)
        return uid
    }
}