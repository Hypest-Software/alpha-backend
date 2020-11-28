package dev.hypestsoftware.hackyeah2020.backend.model

import dev.hypestsoftware.hackyeah2020.backend.model.dto.ReportDto
import org.hibernate.annotations.Type
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "reports")
class Report(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    val uuid: UUID = UUID.randomUUID(),

    @CreatedDate
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val description: String,

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Column(nullable = false)
    var image: ByteArray,

    @OneToOne(optional = false, cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    val location: Location,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReportStatus
) {
    fun toReportDto(): ReportDto {
        return ReportDto(
            uuid,
            createdAt,
            description,
            String(image),
            location.toLocationDto(),
            status.toString()
        )
    }
}
