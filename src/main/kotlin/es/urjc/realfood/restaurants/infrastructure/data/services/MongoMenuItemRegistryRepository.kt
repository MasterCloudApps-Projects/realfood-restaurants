package es.urjc.realfood.restaurants.infrastructure.data.services

import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoMenuItemRestaurant
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoMenuItemRegistryRepository : MongoRepository<MongoMenuItemRestaurant, String> {
    fun findByMenuItemId(menuItemId: String) : MongoMenuItemRestaurant?
    fun deleteByRestaurantId(restaurantId: String)
}