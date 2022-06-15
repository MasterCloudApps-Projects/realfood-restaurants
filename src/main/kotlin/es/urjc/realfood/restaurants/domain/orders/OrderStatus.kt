package es.urjc.realfood.restaurants.domain.orders

enum class OrderStatus(val id: Int, val next: OrderStatus) {
    SENT(3, SENT),
    PREPARED(2, SENT),
    PENDING(1, PREPARED);

    companion object {
        fun fromId(id: Int): OrderStatus? {
            return OrderStatus.values().firstOrNull { it.id == id }
        }
    }
}