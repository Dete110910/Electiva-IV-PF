package com.example.electivaiv.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostComment(
    var authorUid: String,
    var authorName: String = "",
    var authorProfilePhoto: String = "",
    val restaurantName: String,
    val rate: Double,
    val text: String,
    val images: List<String>
) {
}