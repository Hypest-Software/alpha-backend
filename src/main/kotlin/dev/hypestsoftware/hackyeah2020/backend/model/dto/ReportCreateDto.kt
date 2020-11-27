package dev.hypestsoftware.hackyeah2020.backend.model.dto

data class ReportCreateDto(
    val description: String,
    val location: LocationDto,
    val image: String
)
