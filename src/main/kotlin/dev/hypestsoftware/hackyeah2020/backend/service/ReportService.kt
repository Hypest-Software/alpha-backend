package dev.hypestsoftware.hackyeah2020.backend.service

import dev.hypestsoftware.hackyeah2020.backend.exception.base.ApiErrorCode
import dev.hypestsoftware.hackyeah2020.backend.model.Location
import dev.hypestsoftware.hackyeah2020.backend.model.Report
import dev.hypestsoftware.hackyeah2020.backend.model.ReportStatus
import dev.hypestsoftware.hackyeah2020.backend.model.dto.ReportCreateDto
import dev.hypestsoftware.hackyeah2020.backend.repository.ReportRepository
import org.springframework.stereotype.Service
import java.util.UUID

interface ReportService {
    fun createNewReport(report: ReportCreateDto): UUID
    fun updateStatus(uuid: UUID, status: ReportStatus)
    fun getReportByUuid(uuid: UUID): Report
}

@Service
class ReportServiceImpl(private val reportRepository: ReportRepository) : ReportService {

    override fun createNewReport(report: ReportCreateDto): UUID {
        //TODO run image processing
        val (latitude, longitude) = report.location

        val savedReport = reportRepository.save(
            Report(
                description = report.description,
                location = Location(latitude = latitude, longitude = longitude),
                status = ReportStatus.NEW.toString(),
            )
        )
        return savedReport.uuid
    }

    override fun updateStatus(uuid: UUID, status: ReportStatus) {
        val report = reportRepository.findByUuid(uuid) ?: ApiErrorCode.REPORT_ERR_0001.throwException()

        report.status = status.toString()
        reportRepository.save(report)
    }

    override fun getReportByUuid(uuid: UUID): Report {
        return reportRepository.findByUuid(uuid) ?: ApiErrorCode.REPORT_ERR_0001.throwException()
    }

}
