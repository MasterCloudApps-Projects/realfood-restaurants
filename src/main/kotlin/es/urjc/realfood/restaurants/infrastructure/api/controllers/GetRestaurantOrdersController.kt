package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.orders.GetRestaurantOrders
import es.urjc.realfood.restaurants.application.orders.GetRestaurantOrdersRequest
import es.urjc.realfood.restaurants.application.orders.GetRestaurantOrdersResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetRestaurantOrdersController(private val getRestaurantOrders: GetRestaurantOrders) {

    @GetMapping("/api/restaurants/{restaurantId}/orders")
    fun getRestaurantsOrders(@PathVariable restaurantId: String): GetRestaurantOrdersResponse =
        getRestaurantOrders(GetRestaurantOrdersRequest(restaurantId))

}