package com.example.diploma.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.publicapis.org"

val gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .serializeNulls()
    .create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("/entries")
    suspend fun get(): Response
}

data class Response(
    val count: Int
)

object ApiServiceObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}