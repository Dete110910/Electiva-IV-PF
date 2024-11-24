package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.UserService
import com.example.electivaiv.domain.model.User
import javax.inject.Inject

class SaveUserDataUseCase @Inject constructor(
    private val userService: UserService
) {
    fun invoke(user: User){
        userService.saveUserData(user)
    }
}