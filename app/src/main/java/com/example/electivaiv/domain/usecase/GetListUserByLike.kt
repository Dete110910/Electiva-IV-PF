package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.UserService
import com.example.electivaiv.domain.model.User
import javax.inject.Inject

class GetListUserByLike @Inject constructor(
    private val userService: UserService
) {
    suspend fun getUsersByUids(uids: List<String>): List<User> {
        return userService.getUsersByUids(uids)
    }
}