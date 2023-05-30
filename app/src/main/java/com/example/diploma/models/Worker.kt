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
    val email: String,
    val department: String,
    val factory: String,
    val team: String,
    val company: String,
    val specialization: String,
): java.io.Serializable
