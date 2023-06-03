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

// update establishment
@PutMapping("/{id}")
fun updateEstablishmentById(
    @PathVariable("id") establishmentId: UUID,
    @RequestBody updatedFields: Map<String, Any>
): ResponseEntity<Establishment> {
    val existingEstablishment = establishmentRepository.findById(establishmentId).orElse(null)

    if (existingEstablishment == null) {
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    val updatedEstablishment = existingEstablishment.copy(
        description = updatedFields["description"] as? String ?: existingEstablishment.description,
        menu = updatedFields["menu"] as? String ?: existingEstablishment.menu,
        coverPicture = updatedFields["coverPicture"] as? String ?: existingEstablishment.coverPicture
    )
    establishmentRepository.save(updatedEstablishment)
    return ResponseEntity(updatedEstablishment, HttpStatus.OK)
}

// update bookings of an establishment
@PutMapping("/{id}/bookings")
fun updateEstablishmentBookings(
    @PathVariable("id") establishmentId: UUID,
    @RequestBody updatedFields: Map<String, Any>
): ResponseEntity<Any> {
    val existingEstablishment = establishmentRepository.findById(establishmentId).orElse(null)

    if (existingEstablishment == null) {
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    val currentBookings = existingEstablishment.bookings ?: 0
    val updatedBookings = updatedFields["bookings"] as? Int ?: 0
    val totalBookings = currentBookings + updatedBookings
    val capacity = existingEstablishment.capacity

    if (totalBookings <= capacity) {
        val updatedEstablishment = existingEstablishment.copy(bookings = totalBookings)
        establishmentRepository.save(updatedEstablishment)
        return ResponseEntity(updatedEstablishment, HttpStatus.OK)
    } else {
        val errorMessage = "No hay espacio suficiente para su reservación"
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
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

    //get establishment by userID
    @GetMapping("/user/{userId}")
    fun getEstablishmentByUserId(@PathVariable("userId") userId: String): ResponseEntity<List<Establishment>> {
    val establishments = establishmentRepository.findByUserID(userId)
    return if (establishments.isNotEmpty()) {
        ResponseEntity(establishments, HttpStatus.OK)
    } else {
        ResponseEntity(HttpStatus.NOT_FOUND)
    }
    }

    //Get Establishments by Type
    @GetMapping("/type/{establishmentType}")
    fun getEstablishmentByType(@PathVariable("establishmentType") establishmentType: String): ResponseEntity<List<Establishment>> {
    val establishments = establishmentRepository.findByEstablishmentType(establishmentType)
    return if (establishments.isNotEmpty()) {
        ResponseEntity(establishments, HttpStatus.OK)
    } else {
        ResponseEntity(HttpStatus.NOT_FOUND)
    }
    }

}
