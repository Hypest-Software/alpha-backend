package dev.hypestsoftware.hackyeah2020.backend.controller

import dev.hypestsoftware.hackyeah2020.backend.model.Report
import dev.hypestsoftware.hackyeah2020.backend.model.dto.ReportCreateDto
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
    fun createReport(@RequestBody report: ReportCreateDto): ResponseEntity<Report> {
        return ResponseEntity.ok(reportService.createNewReport(report))
    }

    @GetMapping("/{uuid}")
    fun getReportByUuid(@PathVariable uuid: UUID): ResponseEntity<Report> {
        return ResponseEntity.ok(reportService.getReportByUuid(uuid))
    }
}
