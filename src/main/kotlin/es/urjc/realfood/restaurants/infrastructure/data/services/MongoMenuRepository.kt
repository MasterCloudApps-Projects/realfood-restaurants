package es.urjc.realfood.restaurants.infrastructure.data.services

import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoMenu
import org.springframework.data.mongodb.repository.MongoRepository

interface MongoMenuRepository : MongoRepository<MongoMenu, String> {
    fun findMongoMenuByRestaurantId(restaurantId: String) : MongoMenu
    fun deleteByRestaurantId(restaurantId: String)
}