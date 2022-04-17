package es.urjc.realfood.restaurants.application.orders

import es.urjc.realfood.restaurants.domain.Restaurant
import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.orders.Customer
import es.urjc.realfood.restaurants.domain.orders.Order
import es.urjc.realfood.restaurants.domain.orders.OrderLine
import es.urjc.realfood.restaurants.domain.orders.OrderLineExtra
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class NewOrder(
    private val orderRepository: OrderRepository,
    private val restaurantRepository: RestaurantRepository,
) {

    operator fun invoke(req: NewOrderRequest): NewOrderResponse {
        val linesByRestaurant = req.lines.groupBy { restaurantRepository.findByItemId(it.itemId) ?: throw DomainException("Item ${it.itemId} not found") }
        val newOrders = linesByRestaurant.map {
            val restaurant = it.key
            val newOrder = Order(
                id = UUID.randomUUID().toString(),
                lines = map(it.value, restaurant),
                customer = Customer(req.customerId),
                restaurantId = restaurant.id
            )
            orderRepository.add(newOrder)
            newOrder
        }
        return NewOrderResponse(orders = newOrders.map { it.id })
    }

    private fun map(lines: List<NewOrderRequest.OrderLine>, restaurant: Restaurant): List<OrderLine> {
        return lines.map {
            val menuItem =
                restaurant.menu.findItem(it.itemId) ?: throw DomainException("Menu item ${it.itemId} not found")
            val variant =
                it.variantId?.let { menuItem.findVariant(it) ?: throw DomainException("Variant $it not found") }
            val components =
                it.components?.map { menuItem.findComponent(it) ?: throw DomainException("Component $it not found") }
                    ?: emptyList()
            val extras =
                it.extras?.map { menuItem.findExtra(it) ?: throw DomainException("Extra $it not found") } ?: emptyList()
            OrderLine(
                itemId = it.itemId,
                price = menuItem.price,
                qty = it.qty,
                variant = variant?.name,
                components = components.map { it.name },
                extras = extras.map { OrderLineExtra(name = it.name, price = it.price) }
            )
        }
    }

}

data class NewOrderRequest(
    val lines: List<OrderLine>,
    val customerId: String,
) {

    data class OrderLine(
        val itemId: String,
        val qty: Int,
        val variantId: String?,
        val extras: List<String>?,
        val components: List<String>?,
    )
}

data class NewOrderResponse(
    val orders: List<String>,
)