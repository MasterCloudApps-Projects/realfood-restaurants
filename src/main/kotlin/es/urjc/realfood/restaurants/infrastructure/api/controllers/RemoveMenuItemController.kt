package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItem
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItemRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveMenuItemController(private val removeMenuItem: RemoveMenuItem) {

    @DeleteMapping("/api/restaurants/{restaurantId}/menu/items/{itemId}")
    fun postRemoveMenuItem(
        @PathVariable restaurantId: String,
        @PathVariable itemId: String,
    ) = removeMenuItem(
        RemoveMenuItemRequest(
            restaurantId = restaurantId,
            menuItemId = itemId
        )
    )
}