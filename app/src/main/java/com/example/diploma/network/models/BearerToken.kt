package com.example.diploma.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity
data class BearerToken (
    val id: String,
//    val dateTime: LocalDateTime,
    val userId: String,
    val specialization: String,
    val fcmToken: String = "",
    @PrimaryKey
    val bearerId: Int = 1,
)