package es.urjc.realfood.restaurants.application.updateorder

import es.urjc.realfood.restaurants.application.updateorder.dtos.OrderLine
import es.urjc.realfood.restaurants.domain.exceptions.OrderNotFoundException
import es.urjc.realfood.restaurants.domain.orders.OrderStatus
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UpdateOrder(private val orderRepository: OrderRepository) {

    operator fun invoke(req: GetOrderDetailRequest): GetOrderDetailResponse {
        val order = orderRepository.findById(req.id)
            ?: throw OrderNotFoundException("Order ${req.id} not found")
        val newStatus = OrderStatus.fromId(req.newStatus)
            ?: throw IllegalArgumentException("Order status '${req.newStatus}' not valid")
        order.updateStatus(newStatus)
        return GetOrderDetailResponse(
            id = order.id,
            lines = order.lines.map { OrderLine(id = it.itemId, total = it.total()) },
            status = newStatus.id,
            createdAt = order.createdAt.toInstant().toString(),
            total = order.total,
            customerId = order.customer.id
        )
    }

}

