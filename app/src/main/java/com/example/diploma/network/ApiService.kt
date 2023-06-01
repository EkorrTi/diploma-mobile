package com.example.diploma.network

import com.example.diploma.models.Schedule
import com.example.diploma.models.Worker
import com.example.diploma.network.ApiServiceObject.token
import com.example.diploma.network.models.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.LocalDate
import java.util.concurrent.TimeUnit
// "http://10.0.2.2:8760/"    device address
private const val BASE_URL = "http://10.0.2.2:8760/"
// http://26.142.148.179:8760/

val gson: Gson = GsonBuilder()
    .serializeNulls()
    .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
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

object ApiServiceObject {
    var token: String = ""
        get() = "Bearer $field"

    var role: String = ""

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("auth/login/mobile")
    suspend fun unsecureLogin(@Body unsecureLoginRequest: UnsecureLoginRequest): BearerToken

    @GET("manufacture-api/external/schedule/list")
    suspend fun getSchedule(@Header("Authorization") bearer: String = token): List<Schedule>

    @GET("user-api/external/team-members")
    suspend fun getContacts(@Header("Authorization") bearer: String = token): List<Worker>

    @POST("manufacture-api/external/requests/vacancy")
    suspend fun postVacationRequest(
        @Body vacationRequest: VacationRequest,
        @Header("Authorization") bearer: String = token
    ): RequestResponse

    @POST("manufacture-api/external/requests/transfer-team")
    suspend fun postTeamRequest(
        @Body teamRequest: TeamRequest,
        @Header("Authorization") bearer: String = token
    ): RequestResponse

    @GET("manufacture-api/external/requests/my-requests")
    suspend fun getRequestStatus(
        @Query("status") status: String = "PENDING", //  PENDING, IN_PROGRESS, REJECTED, ACCEPTED, SOLVED, CLOSED, UNKNOWN
        @Header("Authorization") bearer: String = token
    ): List<RequestResponse>

    @GET("/manufacture-api/external/production/progress")
    suspend fun getProductionStatus(@Header("Authorization") bearer: String = token): Double
}
data class UnsecureLoginRequest(
    val username: String,
    val password: String,
    val fcmToken: String,
)