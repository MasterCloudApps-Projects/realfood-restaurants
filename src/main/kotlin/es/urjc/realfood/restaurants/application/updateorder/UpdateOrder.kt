package es.urjc.realfood.restaurants.application.updateorder

import es.urjc.realfood.restaurants.domain.exceptions.OrderNotFoundException
import es.urjc.realfood.restaurants.domain.orders.OrderStatus
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UpdateOrder(private val orderRepository: OrderRepository) {

    operator fun invoke(req: UpdateOrderDetailRequest): UpdateOrderDetailResponse {
        val order = orderRepository.findById(req.orderId)
            ?: throw OrderNotFoundException("Order ${req.orderId} not found")
        val newStatus = OrderStatus.fromId(req.newStatus)
            ?: throw IllegalArgumentException("Order status '${req.newStatus}' not valid")
        order.updateStatus(newStatus)
        orderRepository.add(order)
        return UpdateOrderDetailResponse(
            newStatus = newStatus.id
        )
    }

}

