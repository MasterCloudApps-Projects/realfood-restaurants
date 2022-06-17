package es.urjc.realfood.restaurants.domain.event

data class OrderPrepared(
    val clientId: String,
    val orderId: String,
) : DomainEvent()
