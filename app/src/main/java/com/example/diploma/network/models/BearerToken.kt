package com.example.diploma.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity
data class BearerToken (
    @PrimaryKey
    val id: Int = 1,
//    val dateTime: LocalDateTime,
    val userId: String,
    val specialization: String,
    val fcmToken: String
)