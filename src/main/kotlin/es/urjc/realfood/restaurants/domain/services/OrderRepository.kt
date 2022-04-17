package es.urjc.realfood.restaurants.domain.services

import es.urjc.realfood.restaurants.domain.orders.Order

interface OrderRepository {
    fun add(order: Order)
    fun findById(id: String): Order?
    fun findByRestaurantId(id: String): List<Order>
}