package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.AuthenticationService
import com.example.electivaiv.data.network.services.UserService
import com.example.electivaiv.domain.model.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) {

    suspend fun invoke(user: User): Boolean {
        val uid = authenticationService.signUp(user.email, user.password)
        return if (uid != null) {
            userService.saveUserData(user)
            true
        } else
            false
    }
}