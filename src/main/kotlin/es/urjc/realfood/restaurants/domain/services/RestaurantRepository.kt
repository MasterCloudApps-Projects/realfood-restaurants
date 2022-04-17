package es.urjc.realfood.restaurants.domain.services

import es.urjc.realfood.restaurants.domain.Restaurant

interface RestaurantRepository {
    fun save(restaurant: Restaurant)
    fun toList(): List<Restaurant>
    fun findById(id: String): Restaurant?
    fun findByItemId(itemId: String) : Restaurant?
    fun remove(restaurant: Restaurant)
}