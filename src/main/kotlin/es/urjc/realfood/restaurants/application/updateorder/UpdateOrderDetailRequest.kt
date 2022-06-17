package es.urjc.realfood.restaurants.application.updateorder

data class UpdateOrderDetailRequest(val orderId: String, val clientId: String,val newStatus: Int)