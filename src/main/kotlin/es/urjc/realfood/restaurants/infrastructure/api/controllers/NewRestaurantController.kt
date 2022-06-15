package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.createrestaurant.CreateRestaurant
import es.urjc.realfood.restaurants.application.createrestaurant.CreateRestaurantRequest
import es.urjc.realfood.restaurants.application.createrestaurant.CreateRestaurantResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NewRestaurantController(private val createRestaurant: CreateRestaurant) {

    @PostMapping("/api/restaurants")
    fun postRegisterNewRestaurant(@RequestBody request: CreateRestaurantRequest) : CreateRestaurantResponse =
        createRestaurant(request)

}