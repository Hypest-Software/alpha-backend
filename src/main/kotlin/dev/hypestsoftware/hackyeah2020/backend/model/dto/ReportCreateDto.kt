package dev.hypestsoftware.hackyeah2020.backend.model.dto

import dev.hypestsoftware.hackyeah2020.backend.model.Report
import dev.hypestsoftware.hackyeah2020.backend.model.ReportBoarStatus
import dev.hypestsoftware.hackyeah2020.backend.model.ReportStatus

data class ReportCreateDto(
    val description: String,
    val location: LocationDto,
    val image: String,
    val boarStatus: ReportBoarStatus
) {
    fun toEntity() = Report(
        description = description,
        location = location.toEntity(),
        status = ReportStatus.NEW,
        boarStatus = boarStatus
    )

}

