package com.example.diploma.network

import com.example.diploma.network.models.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

private const val BASE_URL = "http://10.0.2.2:8080"

val gson: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .serializeNulls()
    .create()

private val okHttpClient = OkHttpClient.Builder()
    .callTimeout(10, TimeUnit.SECONDS)
    .connectTimeout(10, TimeUnit.SECONDS)
    .writeTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

var token: String = ""
    get() = "Bearer $field"
    set

object ApiServiceObject {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("/login")
    suspend fun postLogin(
        @Body securedLoginRequest: SecuredLoginRequest
    ): String

    @GET("/schedule")
    suspend fun getSchedule(
        @Header("Authorization") bearer: String = token
    )

    @GET("/contacts")
    suspend fun getContacts(// value = "Bearer {token}"
        @Header("Authorization") bearer: String = token
    ): ContactsResponse

    @POST("/request")
    suspend fun postRequest(
        @Header("Authorization") bearer: String = token
    )

    @GET("/request_status")
    suspend fun getRequestStatus(
        @Header("Authorization") bearer: String = token
    )

    @GET("/production")
    suspend fun getProductionStatus(
        @Header("Authorization") bearer: String = token
    )
}

data class SecuredLoginRequest(
    @SerializedName("username")
    val username: List<Byte>,
    @SerializedName("password")
    val password: List<Byte>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SecuredLoginRequest

        if (username != other.username) return false
        if (password != other.password) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        return result
    }
}