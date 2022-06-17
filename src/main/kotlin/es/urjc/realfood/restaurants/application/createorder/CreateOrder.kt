package es.urjc.realfood.restaurants.application.createorder

import es.urjc.realfood.restaurants.domain.exceptions.ItemNotFoundException
import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.orders.Customer
import es.urjc.realfood.restaurants.domain.orders.Order
import es.urjc.realfood.restaurants.domain.orders.OrderLine
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class CreateOrder(
    private val orderRepository: OrderRepository,
    private val restaurantRepository: RestaurantRepository,
) {

    operator fun invoke(req: CreateOrderRequest): CreateOrderResponse {
        val restaurant = restaurantRepository.findById(req.restaurantId)
            ?: throw RestaurantNotFoundException("Restaurant ${req.restaurantId} not found")

        val newOrder = Order(
            restaurantId = req.restaurantId,
            lines = req.lines.map {
                OrderLine(
                    itemId = it.itemId,
                    qty = it.qty,
                    price = restaurant.menu.findItem(it.itemId)?.price
                        ?: throw ItemNotFoundException("Item ${it.itemId} not found")
                )
            },
            customer = Customer(id = req.clientId),
        )
        orderRepository.add(newOrder)
        return CreateOrderResponse(
            orderId = newOrder.id
        )
    }

}

