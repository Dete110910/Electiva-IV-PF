package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import com.example.electivaiv.data.network.services.AuthenticationService
import com.example.electivaiv.data.network.services.UserService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService,
    private val userServiceSP: UserServiceSP,
) {

    suspend fun invoke(email: String, password: String): Any? {
        val uid = authenticationService.login(email, password)
        return if (uid != null) {
            val user = userService.getUserByUid(uid)
            if (user != null) {
                userServiceSP.saveCurrentUserUid(user)
            }
            user
        } else {
            null
        }
    }
}