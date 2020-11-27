package dev.hypestsoftware.hackyeah2020.backend.repository

import dev.hypestsoftware.hackyeah2020.backend.model.Report
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReportRepository : JpaRepository<Report, UUID> {
    fun findByUuid(uuid: UUID): Report?
}
