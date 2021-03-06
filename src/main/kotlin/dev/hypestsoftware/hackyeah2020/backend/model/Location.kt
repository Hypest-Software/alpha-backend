package dev.hypestsoftware.hackyeah2020.backend.model

import dev.hypestsoftware.hackyeah2020.backend.model.dto.LocationDto
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "locations")
class Location(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false)
    val id: Long = 0,

    @Column(nullable = false)
    val latitude: BigDecimal,

    @Column(nullable = false)
    val longitude: BigDecimal
) {
    fun toLocationDto(): LocationDto {
        return LocationDto(latitude, longitude)
    }
}
