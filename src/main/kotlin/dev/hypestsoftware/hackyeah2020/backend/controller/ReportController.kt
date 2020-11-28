package dev.hypestsoftware.hackyeah2020.backend.controller

import dev.hypestsoftware.hackyeah2020.backend.model.dto.LocationDto
import dev.hypestsoftware.hackyeah2020.backend.model.dto.ReportCreateDto
import dev.hypestsoftware.hackyeah2020.backend.model.dto.ReportDto
import dev.hypestsoftware.hackyeah2020.backend.service.ReportService
import dev.hypestsoftware.hackyeah2020.backend.utils.PUBLIC_API_ENDPOINT_V1
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("$PUBLIC_API_ENDPOINT_V1/reports")
class ReportController(private val reportService: ReportService) {

    @PostMapping
    fun createReport(@RequestBody report: ReportCreateDto): ResponseEntity<ReportDto> {
        val createdReport = reportService.createNewReport(report)

        return ResponseEntity.ok(
            ReportDto(
                description = createdReport.description,
                location = LocationDto(
                    latitude = createdReport.location.latitude,
                    longitude = createdReport.location.longitude
                ),
                image = String(createdReport.image)
            )
        )
    }

    @GetMapping("/{uuid}")
    fun getReportByUuid(@PathVariable uuid: UUID): ResponseEntity<ReportDto> {
        val report = reportService.getReportByUuid(uuid)

        return ResponseEntity.ok(
            ReportDto(
                description = report.description,
                location = LocationDto(latitude = report.location.latitude, longitude = report.location.longitude),
                image = String(report.image)
            )
        )
    }
}
