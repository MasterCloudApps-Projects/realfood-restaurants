package es.urjc.realfood.restaurants.infrastructure.data.services

import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoOrder
import org.springframework.data.mongodb.repository.MongoRepository

interface SpringMongoOrderRepository : MongoRepository<MongoOrder, String> {
    fun findAllByRestaurantId(restaurantId: String) : List<MongoOrder>
}