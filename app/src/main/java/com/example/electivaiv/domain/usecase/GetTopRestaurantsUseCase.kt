package com.example.electivaiv.domain.usecase

import android.util.Log
import com.example.electivaiv.data.network.services.ApiService
import com.example.electivaiv.domain.model.RankedRestaurant
import com.example.electivaiv.domain.model.TopRestaurantsResponse
import kotlinx.serialization.json.Json
import javax.inject.Inject

class GetTopRestaurantsUseCase @Inject constructor(
    private val apiService: ApiService
) {
    fun invoke(
        locationId: String = "297704",
        language: String = "en_US",
        currency: String = "USD",
        offset: Int = 0
    ): List<RankedRestaurant> {
        val response = apiService.getReviews(locationId, language, currency, offset)
        return if (response.isSuccessful) {
            response.body?.string()?.let { body ->
                val reviewsResponse = Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }.decodeFromString<TopRestaurantsResponse>(body)
                reviewsResponse.results!!.data
            } ?: emptyList()
        } else {
            Log.d("TEST", "Failed to fetch reviews: ${response.message}")
            emptyList()
        }
    }
}