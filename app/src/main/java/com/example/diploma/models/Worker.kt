package com.example.diploma.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Worker(
    @PrimaryKey
    val id: Int,
    val name: String,
    val phone: String,
    val position: String
)
