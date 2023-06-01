package com.example.diploma.network.models

import java.time.LocalDate

data class VacationRequest(
    val reason: String,
    val startDate: String,
    val endDate: String,
)
