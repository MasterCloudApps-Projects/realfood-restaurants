package es.urjc.realfood.restaurants.domain.orders

import java.util.*

data class Order(
    val id: String,
    val restaurantId: String,
    val lines: List<OrderLine>,
    val customer: Customer,
) {
    val status: String = "PENDING"
    val createdAt = Calendar.getInstance().time
    val total = lines.sumOf { it.total() }
}