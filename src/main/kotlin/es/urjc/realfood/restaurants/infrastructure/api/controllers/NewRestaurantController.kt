package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.restaurants.RegisterNewRestaurant
import es.urjc.realfood.restaurants.application.restaurants.RegisterNewRestaurantRequest
import es.urjc.realfood.restaurants.application.restaurants.RegisterNewRestaurantResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NewRestaurantController(private val registerNewRestaurant: RegisterNewRestaurant) {

    @PostMapping("/api/restaurants")
    fun postRegisterNewRestaurant(@RequestBody request: RegisterNewRestaurantRequest) : RegisterNewRestaurantResponse =
        registerNewRestaurant(request)

}