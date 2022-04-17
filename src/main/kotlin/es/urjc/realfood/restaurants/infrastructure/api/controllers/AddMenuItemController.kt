package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.menu.AddMenuItem
import es.urjc.realfood.restaurants.application.menu.AddMenuItemRequest
import es.urjc.realfood.restaurants.application.menu.AddMenuItemResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AddMenuItemController(private val addMenuItem: AddMenuItem) {

    @PostMapping("/api/restaurants/{restaurantId}/menu/blocks/{blockId}/items")
    fun postAddMenuItem(
        @PathVariable restaurantId: String,
        @PathVariable blockId: String,
        @RequestBody menuItemRequest: AddMenuItemRequest.MenuItem,
    ): AddMenuItemResponse =
        addMenuItem(AddMenuItemRequest(
            restaurantId = restaurantId,
            blockId = blockId,
            menuItem = menuItemRequest
        ))
}