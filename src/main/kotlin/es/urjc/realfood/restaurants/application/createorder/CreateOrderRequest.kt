package es.urjc.realfood.restaurants.application.createorder

import es.urjc.realfood.restaurants.application.updateorder.dtos.OrderLine

data class CreateOrderRequest(
    val restaurantId: String,
    val clientId: String,
    val lines: List<OrderLine>,
)