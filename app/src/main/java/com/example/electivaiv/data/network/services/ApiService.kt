package com.example.electivaiv.data.network.services

import com.example.electivaiv.data.network.clients.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import javax.inject.Inject

class ApiService @Inject constructor(
    private val apiClient: ApiClient
) {
    fun getReviews(
        locationId: String,
        language: String,
        currency: String,
        offset: Int
    ): Response {
        val mediaType = "application/x-www-form-urlencoded".toMediaType()
        val body = "location_id=$locationId&language=$language&currency=$currency&offset=$offset"
            .toRequestBody(mediaType)

        return apiClient.makeRequest(
            url = "https://worldwide-restaurants.p.rapidapi.com/search",
            body = body
        )
    }
}