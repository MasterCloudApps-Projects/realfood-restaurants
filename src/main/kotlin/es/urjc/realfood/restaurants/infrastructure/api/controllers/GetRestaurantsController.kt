package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.restaurants.GetRestaurants
import es.urjc.realfood.restaurants.application.restaurants.GetRestaurantsRequest
import es.urjc.realfood.restaurants.application.restaurants.GetRestaurantsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GetRestaurantsController(private val getRestaurants: GetRestaurants) {

    @GetMapping("/api/restaurants")
    fun postRegisterNewRestaurant(@RequestBody request: GetRestaurantsRequest): GetRestaurantsResponse =
        getRestaurants(request)

}