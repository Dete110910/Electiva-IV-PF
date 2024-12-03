package com.example.electivaiv.ui.screens.authorCommentProfile

import com.example.electivaiv.domain.model.PostComment

data class AuthorCommentProfileUi(
    var otherComments : List<PostComment> = emptyList(),
    val likes: List<String> = emptyList()
)
