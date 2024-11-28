package com.example.electivaiv.domain.model

data class PostComment(
    var authorUid: String,
    var authorName: String = "",
    val restaurantName: String,
    val rate: Number,
    val text: String,
    val images: List<String>
) {
}