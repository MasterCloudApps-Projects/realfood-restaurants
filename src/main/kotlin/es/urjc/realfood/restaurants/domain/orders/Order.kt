package es.urjc.realfood.restaurants.domain.orders

import java.time.Instant
import java.util.*

data class Order(
    val id: String = UUID.randomUUID().toString(),
    val restaurantId: String,
    val lines: List<OrderLine>,
    val customer: Customer,
) {

    private var lastUpdate: Date = Date()
    private var status: OrderStatus = OrderStatus.PENDING
    val createdAt = Calendar.getInstance().time
    val total = lines.sumOf { it.total() }
    fun updateStatus(status: OrderStatus) {
        if(this.status.next != status)
            throw IllegalStateException("Order '$id' invalid next status '$status', it must be '${this.status.next}'")
        if(status == this.status)
            return

        this.status = status
        this.lastUpdate = Date()
    }

    fun getLastUpdate(): Instant {
        return lastUpdate.toInstant()
    }

    fun status(): OrderStatus {
        return this.status
    }
}