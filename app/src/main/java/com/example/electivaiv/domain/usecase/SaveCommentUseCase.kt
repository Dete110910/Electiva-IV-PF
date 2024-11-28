package com.example.electivaiv.domain.usecase

import com.example.electivaiv.data.local.services.UserServiceSP
import com.example.electivaiv.data.network.services.DataBaseService
import com.example.electivaiv.domain.model.PostComment
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val dataBaseService: DataBaseService,
    private val userServiceSP: UserServiceSP
) {
    suspend fun invoke(comment: PostComment): Boolean {
        var commentWithAdditionalValues = comment
        commentWithAdditionalValues.authorName = userServiceSP.getCurrentUserName()!!
        commentWithAdditionalValues.authorUid = userServiceSP.getCurrentUserUid()!!
        return dataBaseService.saveComment(commentWithAdditionalValues)
    }
}