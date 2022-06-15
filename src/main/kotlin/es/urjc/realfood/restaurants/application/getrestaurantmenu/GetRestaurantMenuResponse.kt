package es.urjc.realfood.restaurants.application.getrestaurantmenu

import es.urjc.realfood.restaurants.application.getrestaurantmenu.dto.MenuItem

data class GetRestaurantMenuResponse(
    val id: String,
    val items: List<MenuItem>,
)

