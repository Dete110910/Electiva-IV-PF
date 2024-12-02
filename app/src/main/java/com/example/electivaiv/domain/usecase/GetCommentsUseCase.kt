package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.network.services.DataBaseService
import com.example.electivaiv.domain.model.PostComment
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
        private val dataBaseService: DataBaseService
) {
    suspend fun invoke(): List<PostComment> {
        return dataBaseService.getComments()
    }
}