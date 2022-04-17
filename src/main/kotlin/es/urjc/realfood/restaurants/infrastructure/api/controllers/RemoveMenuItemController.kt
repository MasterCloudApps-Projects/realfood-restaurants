package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.menu.AddMenuItemRequest
import es.urjc.realfood.restaurants.application.menu.RemoveMenuItem
import es.urjc.realfood.restaurants.application.menu.RemoveMenuItemRequest
import es.urjc.realfood.restaurants.application.menu.RemoveMenuItemResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveMenuItemController(private val removeMenuItem: RemoveMenuItem) {

    @PostMapping("/api/restaurants/{restaurantId}/menu/blocks/{blockId}/items/{itemId}")
    fun postRemoveMenuItem(
        @PathVariable restaurantId: String,
        @PathVariable blockId: String,
        @RequestBody menuItemRequest: AddMenuItemRequest.MenuItem, @PathVariable itemId: String,
    ): RemoveMenuItemResponse =
        removeMenuItem(RemoveMenuItemRequest(
            restaurantId = restaurantId,
            blockId = blockId,
            menuItemId = itemId
        ))
}