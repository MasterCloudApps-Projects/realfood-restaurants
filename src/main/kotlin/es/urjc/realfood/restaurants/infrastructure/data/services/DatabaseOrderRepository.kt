package es.urjc.realfood.restaurants.infrastructure.data.services

import es.urjc.realfood.restaurants.domain.orders.Customer
import es.urjc.realfood.restaurants.domain.orders.Order
import es.urjc.realfood.restaurants.domain.orders.OrderLine
import es.urjc.realfood.restaurants.domain.orders.OrderLineExtra
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoOrder
import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoOrderLine
import es.urjc.realfood.restaurants.infrastructure.data.entities.MongoOrderLineExtra
import org.springframework.stereotype.Repository

@Repository
class DatabaseOrderRepository(
    private val mongoOrderRepository: SpringMongoOrderRepository,
) : OrderRepository {

    override fun add(order: Order) {
        mongoOrderRepository.save(MongoOrder(
            id = order.id,
            status = order.status().id,
            lines = order.lines.map {
                MongoOrderLine(
                    itemId = it.itemId,
                    qty = it.qty,
                    price = it.price
                )
            },
            total = order.total,
            customerId = order.customer.id,
            restaurantId = order.restaurantId,
            createdAt = order.createdAt
        ))
    }

    override fun findById(id: String): Order? {
        val order = mongoOrderRepository.findById(id).orElse(null)
        return order?.let {
            Order(id = it.id,
                lines = mapLines(it.lines),
                customer = Customer(it.id),
                restaurantId = it.restaurantId)
        }
    }

    override fun findByRestaurantId(id: String): List<Order> {
        return mongoOrderRepository.findAllByRestaurantId(id).map {
            Order(id = it.id, lines = mapLines(it.lines), customer = Customer(it.id),
                restaurantId = id)
        }
    }

    private fun mapLines(lines: List<MongoOrderLine>): List<OrderLine> {
        return lines.map {
            OrderLine(
                itemId = it.itemId,
                price = it.price,
                qty = it.qty,
            )
        }
    }

}