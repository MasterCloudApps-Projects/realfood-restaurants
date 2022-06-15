package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.removerestaurant.RemoveRestaurant
import es.urjc.realfood.restaurants.application.removerestaurant.RemoveRestaurantRequest
import es.urjc.realfood.restaurants.application.removerestaurant.RemoveRestaurantResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveRestaurantController(private val removeRestaurant: RemoveRestaurant) {

    @DeleteMapping("/api/restaurants/{restaurantId}")
    fun deleteRestaurant(
        @PathVariable restaurantId: String,
    ): RemoveRestaurantResponse =
        removeRestaurant(
            RemoveRestaurantRequest(
            restaurantId = restaurantId,
        )
        )
}