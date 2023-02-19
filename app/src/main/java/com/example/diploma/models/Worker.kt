package com.example.diploma.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Worker(
    @PrimaryKey @SerializedName("userId") val userId: Int,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    val username: String,
    val phone: String,
    @SerializedName("roleId") val roleId: String,
    @SerializedName("companyId") val companyId: String,
    @SerializedName("departmentId") val departmentId: String,
    @SerializedName("teamId") val teamId: String,
)
