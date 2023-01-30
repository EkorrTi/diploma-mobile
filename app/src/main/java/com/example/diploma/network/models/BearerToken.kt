package com.example.diploma.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BearerToken (
    @PrimaryKey
    val id: Int = 1,
    val token: String
)