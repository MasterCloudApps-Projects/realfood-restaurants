package es.urjc.realfood.restaurants.application.getorderdetail

import es.urjc.realfood.restaurants.application.getorderdetail.dto.OrderLine

data class GetOrderDetailResponse(
    val id: String,
    val lines: List<OrderLine>,
    val customerId: String,
    val status: Int,
    val createdAt: String,
    val total: Int,
)

