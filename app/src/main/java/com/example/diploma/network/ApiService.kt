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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.time.LocalDate
import java.util.concurrent.TimeUnit
// 10.0.2.2    device address
private const val BASE_URL = "http://10.0.2.2:8080"

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

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

// TODO: adjust paths according to API
interface ApiService {
    @POST("/login")
    suspend fun postLogin( @Body securedLoginRequest: SecuredLoginRequest ): String
    // С токеном вместе еще шли юзера полную инфу

    // TODO logout?

    @GET("/schedule")
    suspend fun getSchedule( @Header("Authorization") bearer: String = token ): List<Schedule>
    // Ну понятно тут, по соответствующей команде

    // TODO getEvents - список событий на эту неделю по порядку, дни недели > дата\время

    @GET("/contacts")
    suspend fun getContacts( @Header("Authorization") bearer: String = token ): List<Worker>
    // Все люди в моей команде, кроме меня конечно

    @POST("/vacation")
    suspend fun postVacationRequest(
        @Body startDate: LocalDate,
        @Body endDate: LocalDate,
        @Body type: String,
        @Header("Authorization") bearer: String = token
    ): String
    // Ответ типо Принял\Ошибка т.д.

    @POST("/team")
    suspend fun postTeamRequest(
        @Body type: String,
        @Header("Authorization") bearer: String = token
    )
    // Ответ типо Принял\Ошибка т.д.

    @GET("/requestStatus")
    suspend fun getRequestStatus( @Header("Authorization") bearer: String = token ): String
    // Ответ List<VacationRequest + TeamRequests? (если они совмещяемы)> сортированный по дате недавно->давно

    @GET("/production")
    suspend fun getProductionStatus( @Header("Authorization") bearer: String = token ): Double
    // Просто проценты наверно

    @POST("/firebaseToken")
    suspend fun postFirebaseToken( @Body token: String )
    // Ответ типо Принял\Ошибка т.д.
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