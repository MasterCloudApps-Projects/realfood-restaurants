package es.urjc.realfood.restaurants.application.addmenuitem

import es.urjc.realfood.restaurants.application.addmenuitem.dto.MenuItem

data class AddMenuItemRequest(
    val restaurantId: String,
    val menuItem: MenuItem,
)

