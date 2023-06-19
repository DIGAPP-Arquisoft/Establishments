package com.example.demo.establishments

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@Component
class BookingMessageConsumer(private val establishmentRepository: EstablishmentRepository) {

    @RabbitListener(queues = ["digaap_mq"])
    
   
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
}
