package dev.hypestsoftware.hackyeah2020.backend.model

import org.springframework.data.annotation.CreatedDate
import java.sql.Timestamp
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "reports")
class Report(
        @Id
        @GeneratedValue
        @Column(columnDefinition = "uuid", updatable = false)
        val uuid: UUID = UUID.randomUUID(),

        @CreatedDate
        @Column(nullable = false)
        val createdAt: Timestamp,

        @Column(nullable = false)
        val description: String,

        @Column(nullable = false)
        val imageUrl: String,

        @OneToOne(optional = false)
        val location: Location,

        @Column(nullable = false)
        val status: String
)