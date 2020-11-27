package dev.hypestsoftware.hackyeah2020.backend.repository

import dev.hypestsoftware.hackyeah2020.backend.model.Report
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ReportRepository : CrudRepository<Report, UUID> {
    fun findByUuid(uuid: UUID): Report?
}
