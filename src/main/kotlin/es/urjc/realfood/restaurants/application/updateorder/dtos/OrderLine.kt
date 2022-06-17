package es.urjc.realfood.restaurants.application.updateorder.dtos

data class OrderLine(
    val itemId: String,
    val qty: Int,
)