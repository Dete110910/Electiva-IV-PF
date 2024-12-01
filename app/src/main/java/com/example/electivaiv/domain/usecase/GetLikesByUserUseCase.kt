package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.LikeUserService
import com.example.electivaiv.domain.model.likeUser
import javax.inject.Inject

class GetLikesByUserUseCase @Inject constructor(
    private val likeUserService: LikeUserService
) {
    suspend fun invoke(userId: String): List<String> {
        return likeUserService.getUidsByUserId(userId)
    }

    suspend fun saveLikeUser(likeUser: likeUser) {
        likeUserService.saveLikeUser(likeUser)
    }

    suspend fun addLike(userId: String, authorUid: String) {
        likeUserService.addLike(userId, authorUid)
    }
    suspend fun removeLike(userId: String, authorUid: String) {
        likeUserService.removeLike(userId, authorUid)
    }
}