package es.urjc.realfood.restaurants.application.removemenuitem

data class RemoveMenuItemRequest(
    val restaurantId: String,
    val menuItemId: String,
)