package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.addmenuitem.AddMenuItem
import es.urjc.realfood.restaurants.application.addmenuitem.AddMenuItemRequest
import es.urjc.realfood.restaurants.application.addmenuitem.AddMenuItemResponse
import es.urjc.realfood.restaurants.application.addmenuitem.dto.MenuItem
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AddMenuItemController(private val addMenuItem: AddMenuItem) {

    @PostMapping("/api/restaurants/{restaurantId}/menu/items")
    fun postAddMenuItem(
        @PathVariable restaurantId: String,
        @RequestBody menuItemRequest: MenuItem,
    ): AddMenuItemResponse = addMenuItem(
        AddMenuItemRequest(
            restaurantId = restaurantId, menuItem = menuItemRequest
        )
    )
}