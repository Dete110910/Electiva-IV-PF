package com.example.electivaiv.domain.model

data class PostComment(
    val authorUid: String,
    val authorName: String = "",
    val restaurantName: String,
    val rate: Number,
    val text: String,
    val images: List<String>
) {
}