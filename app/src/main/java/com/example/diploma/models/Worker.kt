package com.example.diploma.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Worker(
    @PrimaryKey val userId: Int,
    val firstName: String,
    val lastName: String,
    val username: String,
    val phone: String,
    val roleId: String,
    val companyId: String,
    val departmentId: String,
    val teamId: String,
)
