package com.example.diploma.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.diploma.models.Worker

@Entity
data class ContactsResponse(
    @PrimaryKey
    val id: Int = 1,
    val dataMap: HashMap<String, List<Worker>>,
    val workerList: List<Worker>,
)
