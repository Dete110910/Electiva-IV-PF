package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.LikeUserService
import javax.inject.Inject

class GetLikesByUserUseCase @Inject constructor(
    private val likeUserService: LikeUserService
) {
    suspend fun invoke(userId: String): List<String> {
        return likeUserService.getUidsByUserId(userId)
    }
}