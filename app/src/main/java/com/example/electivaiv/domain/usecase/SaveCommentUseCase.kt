package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.DataBaseService
import com.example.electivaiv.domain.model.PostComment
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val dataBaseService: DataBaseService
) {
    suspend fun invoke(comment: PostComment): Boolean {
        return dataBaseService.saveComment(comment)
    }
}