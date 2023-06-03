package com.example.demo.establishments

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface EstablishmentRepository : CrudRepository<Establishment, UUID> {
    fun findByUserID(userID: String): List<Establishment>
    fun findByEstablishmentType(establishmentType: String): List<Establishment>
}