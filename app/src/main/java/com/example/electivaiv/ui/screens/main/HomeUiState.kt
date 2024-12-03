package com.example.electivaiv.ui.screens.main

import com.example.electivaiv.domain.model.PostComment

data class HomeUiState(
    val commentsList : List<PostComment> = emptyList(),
    val isLoading: Boolean = true,
    val likes: List<String> = emptyList()
)
