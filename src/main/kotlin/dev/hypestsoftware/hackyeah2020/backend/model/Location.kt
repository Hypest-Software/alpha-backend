package dev.hypestsoftware.hackyeah2020.backend.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "locations")
class Location(

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(updatable = false)
        val id: Long,

        @Column(nullable = false)
        val latitude: Double,

        @Column(nullable = false)
        val longitude: Double
)