package com.example.diploma.models

import java.time.DayOfWeek
import java.time.LocalTime

data class Schedule(
    val id: Int,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val teamId: Int,
    val departmentId: Int,
    val factoryId: Int
)
