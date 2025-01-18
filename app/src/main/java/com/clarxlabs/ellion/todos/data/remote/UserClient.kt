package com.clarxlabs.ellion.todos.data.remote

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object Retrofit {
    private const val BASE_URL = "https://ellion.gigalixirapp.com/api/"

    fun getClient() =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
            .create(UserApi::class.java)
}
