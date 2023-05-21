package com.example.demo.establishments

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/establishments")
class EstablishmentController(@Autowired private val establishmentRepository: EstablishmentRepository) {

    //get all establishment
    @GetMapping("")
    fun getAllEstablishment(): List<Establishment> =
        establishmentRepository.findAll().toList()

    //create establishment
    @PostMapping("")
    fun createEstablishment(@RequestBody establishment: Establishment): ResponseEntity<Establishment> {
        val savedEstablishment = establishmentRepository.save(establishment)
        return ResponseEntity(savedEstablishment, HttpStatus.CREATED)
    }

    //get establishment by id
    @GetMapping("/{id}")
    fun getEstablishmentById(@PathVariable("id") establishmentId: UUID): ResponseEntity<Establishment> {
        val establishment = establishmentRepository.findById(establishmentId).orElse(null)
        return if (establishment != null) {
            ResponseEntity(establishment, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //update establishment
    @PutMapping("/{id}")
    fun updateEstablishmentById(
        @PathVariable("id") establishmentId: UUID,
        @RequestBody establishment: Establishment
    ): ResponseEntity<Establishment> {
        val existingEstablishment = establishmentRepository.findById(establishmentId).orElse(null)

        if (existingEstablishment == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedEstablishment = existingEstablishment.copy(
            description = establishment.description,
            menu = establishment.menu,
            coverPicture = establishment.coverPicture
        )
        establishmentRepository.save(updatedEstablishment)
        return ResponseEntity(updatedEstablishment, HttpStatus.OK)
    }

    //delete establishment
    @DeleteMapping("/{id}")
    fun deletedEstablishmentById(@PathVariable("id") establishmentId: UUID): ResponseEntity<Establishment> {
        if (!establishmentRepository.existsById(establishmentId)) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        establishmentRepository.deleteById(establishmentId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
