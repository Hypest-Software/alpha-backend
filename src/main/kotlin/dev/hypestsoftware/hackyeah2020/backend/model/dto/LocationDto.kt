package dev.hypestsoftware.hackyeah2020.backend.model.dto

import dev.hypestsoftware.hackyeah2020.backend.model.Location
import java.math.BigDecimal

data class LocationDto(
    val latitude: BigDecimal,
    val longitude: BigDecimal
) {
    fun toEntity() = Location(latitude = latitude, longitude = longitude)
}
