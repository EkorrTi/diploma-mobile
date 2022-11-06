package com.example.diploma.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BearerToken (
    @PrimaryKey
    val token: String
)