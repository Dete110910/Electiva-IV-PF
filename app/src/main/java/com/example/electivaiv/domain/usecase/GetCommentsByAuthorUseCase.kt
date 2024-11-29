package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.DataBaseService
import com.example.electivaiv.domain.model.PostComment
import javax.inject.Inject

class GetCommentsByAuthorUseCase @Inject constructor(
    private val dataBaseService: DataBaseService
) {
    suspend fun invoke(uid: String): List<PostComment> {
        return dataBaseService.getCommentsByAuthor(uid)
    }
}