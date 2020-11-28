package dev.hypestsoftware.hackyeah2020.backend.model.dto

import java.time.LocalDateTime
import java.util.UUID


class ReportDto(
    val uuid: UUID,
    val createdAt: LocalDateTime,
    val description: String,
    val image: String,
    val location: LocationDto,
    val status: String
)
