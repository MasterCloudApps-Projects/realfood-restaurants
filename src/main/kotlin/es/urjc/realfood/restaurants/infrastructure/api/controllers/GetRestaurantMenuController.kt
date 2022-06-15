package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.getrestaurantmenu.GetRestaurantMenu
import es.urjc.realfood.restaurants.application.getrestaurantmenu.GetRestaurantMenuRequest
import es.urjc.realfood.restaurants.application.getrestaurantmenu.GetRestaurantMenuResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetRestaurantMenuController(private val getRestaurantMenu: GetRestaurantMenu) {

    @GetMapping("/api/restaurants/{restaurantId}/menu")
    fun postRegisterNewRestaurant(@PathVariable restaurantId: String): GetRestaurantMenuResponse =
        getRestaurantMenu(GetRestaurantMenuRequest(restaurantId))

}