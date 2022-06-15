package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.addmenuitem.AddMenuItemRequest
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItem
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItemRequest
import es.urjc.realfood.restaurants.application.removemenuitem.RemoveMenuItemResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RemoveMenuItemController(private val removeMenuItem: RemoveMenuItem) {

    @PostMapping("/api/restaurants/{restaurantId}/menu/items/{itemId}")
    fun postRemoveMenuItem(
        @PathVariable restaurantId: String,
        @PathVariable blockId: String,
        @RequestBody menuItemRequest: AddMenuItemRequest.MenuItem, @PathVariable itemId: String,
    ): RemoveMenuItemResponse =
        removeMenuItem(
            RemoveMenuItemRequest(
            restaurantId = restaurantId,
            blockId = blockId,
            menuItemId = itemId
        )
        )
}