package com.example.demo.establishments


import java.util.UUID
import org.hibernate.annotations.GenericGenerator
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.*

@Entity
@Table(name = "establishments")
data class Establishment(
    @Id
    @GeneratedValue   //(generator = "UUID")
    //@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition="uuid",updatable=false,nullable=false)
    val id: UUID=UUID.randomUUID(),
    val userID: String,
    val establishmentName: String,
    val opening: String,
    val closing: String,
    val establishmentType: String,
    val capacity: Int,
    val description: String,
    val menu: String,
    val coverPicture: String,
    val location: String,
    val city: Int
) {
    constructor(
        userID: String,
        establishmentName: String,
        opening: String,
        closing: String,
        establishmentType: String,
        capacity: Int,
        description: String,
        menu: String,
        coverPicture: String,
        location: String,
        city: Int
    ) : this(
        id=UUID.randomUUID(),
        userID,
        establishmentName,
        opening,
        closing,
        establishmentType,
        capacity,
        description,
        menu,
        coverPicture,
        location,
        city
    )
}
