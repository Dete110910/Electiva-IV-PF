package com.example.electivaiv.ui.screens.usersWithLikes

import com.example.electivaiv.domain.model.PostComment
import com.example.electivaiv.domain.model.User

data class UsersWithLikesUiState(
    val usersWithLikes: List<User> = emptyList(),
    var otherComments : List<PostComment> = emptyList(),
)