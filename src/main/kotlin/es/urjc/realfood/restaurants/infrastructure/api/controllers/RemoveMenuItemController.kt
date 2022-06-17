package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.addmenuitem.AddMenuItemRequest
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItem
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItemRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveMenuItemController(private val removeMenuItem: RemoveMenuItem) {

    @PostMapping("/api/restaurants/{restaurantId}/menu/items/{itemId}")
    fun postRemoveMenuItem(
        @PathVariable restaurantId: String,
        @RequestBody menuItemRequest: AddMenuItemRequest, @PathVariable itemId: String,
    ) = removeMenuItem(
        RemoveMenuItemRequest(
            restaurantId = restaurantId,
            menuItemId = itemId
        )
    )
}