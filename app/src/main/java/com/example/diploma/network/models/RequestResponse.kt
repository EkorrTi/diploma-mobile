package com.example.diploma.network.models

import java.time.LocalDate

data class RequestResponse(
    val id: Int?,
    val reason: String,
    val applicant: String,
    val responsible: String,
    val applicantFullName: String,
    val responsibleFullName: String,
    //val startDate: LocalDate,
    //val endDate: LocalDate,
    val status: String,
    val requestType: String,
    val transferringEmployeeUsername: String,
    val teamId: String,
)
