package es.urjc.realfood.restaurants.infrastructure.api.controllers.createorder

import es.urjc.realfood.restaurants.application.updateorder.dtos.OrderLine

data class CreateOrderRequest(
    val restaurantId: String,
    val lines: List<OrderLine>,
)