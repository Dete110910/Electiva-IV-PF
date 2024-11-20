package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.AuthenticationService
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationService: AuthenticationService
) {

    suspend fun invoke(email: String, password: String){
        authenticationService.signUp(email, password)
    }
}