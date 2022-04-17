package es.urjc.realfood.restaurants.infrastructure.data.entities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class JpaRestaurant(
    @Id val id: String,
    val name: String,
    val streetName: String,
    val number: Int,
    val city: String,
    val zipCode: String,
    val additional: String,
    val businessHours: String,
    val categoryId: Int,
)