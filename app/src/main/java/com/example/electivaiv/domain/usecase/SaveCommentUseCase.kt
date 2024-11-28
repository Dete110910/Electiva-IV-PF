package com.example.electivaiv.domain.usecase

import android.net.Uri
import com.example.electivaiv.data.local.services.UserServiceSP
import com.example.electivaiv.data.network.services.DataBaseService
import com.example.electivaiv.domain.model.PostComment
import javax.inject.Inject

class SaveCommentUseCase @Inject constructor(
    private val dataBaseService: DataBaseService,
    private val userServiceSP: UserServiceSP
) {
    suspend fun invoke(comment: PostComment): Boolean {
        comment.authorName = userServiceSP.getCurrentUserName()!!
        comment.authorUid = userServiceSP.getCurrentUserUid()!!
        return dataBaseService.saveComment(comment)
    }

    suspend fun saveImages(uris: List<Uri>): MutableList<String> {
        return dataBaseService.saveImages(uris)
    }
}