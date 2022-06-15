package es.urjc.realfood.restaurants.infrastructure.data.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class MongoMenuItem(
    val id: String,
    val restaurantId: String,
    val name: String,
    val price: Int,
)