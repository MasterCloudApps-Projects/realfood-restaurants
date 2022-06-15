package es.urjc.realfood.restaurants.application.updateorder

import es.urjc.realfood.restaurants.application.updateorder.dtos.OrderLine

data class GetOrderDetailResponse(
    val id: String,
    val lines: List<OrderLine>,
    val customerId: String,
    val status: Int,
    val createdAt: String,
    val total: Int,
)