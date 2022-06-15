package es.urjc.realfood.restaurants.application.getorderdetail

import es.urjc.realfood.restaurants.application.getorderdetail.dto.OrderLine
import es.urjc.realfood.restaurants.domain.exceptions.OrderNotFoundException
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetOrderDetail(private val orderRepository: OrderRepository) {

    operator fun invoke(req: GetOrderDetailRequest): GetOrderDetailResponse {
        val order = orderRepository.findById(req.id)
            ?: throw OrderNotFoundException("Order ${req.id} not found")

        return GetOrderDetailResponse(
            id = order.id,
            lines = order.lines.map { OrderLine(id = it.itemId, total = it.total()) },
            status = order.status().id,
            createdAt = order.createdAt.toInstant().toString(),
            total = order.total,
            customerId = order.customer.id
        )
    }

}

