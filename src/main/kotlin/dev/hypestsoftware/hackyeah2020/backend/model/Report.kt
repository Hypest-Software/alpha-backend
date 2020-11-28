package dev.hypestsoftware.hackyeah2020.backend.model

import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp
import java.util.UUID
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
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
    val createdAt: Timestamp = Timestamp(0),

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    var imageUrl: String = "",

    @OneToOne(optional = false, cascade = [CascadeType.ALL])
    val location: Location,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: ReportStatus
)
