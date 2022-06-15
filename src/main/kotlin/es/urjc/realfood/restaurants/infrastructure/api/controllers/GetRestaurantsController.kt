package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.getrestaurants.GetRestaurants
import es.urjc.realfood.restaurants.application.getrestaurants.GetRestaurantsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetRestaurantsController(private val getRestaurants: GetRestaurants) {

    @GetMapping("/api/restaurants")
    fun postRegisterNewRestaurant(): GetRestaurantsResponse =
        getRestaurants()

}