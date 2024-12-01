package com.example.electivaiv.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRestaurantsResponse(
    val results: Results? = null
)

@Serializable
data class Results(
    val data: List<RankedRestaurant>? = null
)

@Serializable
data class RankedRestaurant(
    @SerialName("location_string") val location: String? = "Unknown location",
    val name: String? = "Unknown Restaurant",
    @SerialName("num_reviews") val reviewsNum: String? = "0",
    val rating: String = "0",
    @SerialName("is_closed") val isClosed: Boolean? = true,
    val ranking: String? = "",
    val price: String? = "$0 - $0",
    val photo: ApiPhotos? = null
)

@Serializable
data class ApiPhotos(
    @SerialName("images") val thumbnailImageUrl: OriginalImage? = null
)

@Serializable
data class OriginalImage(
    @SerialName("original") val url: ImageUrl? = null
)

@Serializable
data class ImageUrl(
    val url: String? = ""
)