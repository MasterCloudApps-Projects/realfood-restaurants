package es.urjc.realfood.restaurants.application.orders

import es.urjc.realfood.restaurants.domain.services.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetRestaurantOrders(private val orderRepository: OrderRepository) {

    operator fun invoke(req: GetRestaurantOrdersRequest): GetRestaurantOrdersResponse {
        val orders = orderRepository.findByRestaurantId(req.id)
        return GetRestaurantOrdersResponse(
            items = orders.map {
                GetRestaurantOrdersResponse.Order(id = it.id,
                    createdAt = it.createdAt.toString(),
                    status = it.status)
            }
        )
    }
}

data class GetRestaurantOrdersRequest(val id: String)
data class GetRestaurantOrdersResponse(val items: List<Order>) {
    data class Order(
        val id: String,
        val createdAt: String,
        val status: String,
    )
}


