package com.example.electivaiv.data.network.clients

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClient @Inject constructor() {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-rapidapi-key", "c3208d7e5cmshff69ba28777afbep1b7c31jsnfb346d038b9a")
                .addHeader("x-rapidapi-host", "worldwide-restaurants.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    fun makeRequest(url: String, body: RequestBody): Response {
        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        return okHttpClient.newCall(request).execute()
    }
}