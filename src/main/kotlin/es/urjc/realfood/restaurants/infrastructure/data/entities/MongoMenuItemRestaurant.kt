package es.urjc.realfood.restaurants.infrastructure.data.entities

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class MongoMenuItemRestaurant(
    val menuItemId: String,
    val restaurantId: String,
)