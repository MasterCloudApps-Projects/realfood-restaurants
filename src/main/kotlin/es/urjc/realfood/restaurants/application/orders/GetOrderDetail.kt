package es.urjc.realfood.restaurants.application.orders

import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.orders.OrderLine
import es.urjc.realfood.restaurants.domain.services.OrderRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetOrderDetail(private val orderRepository: OrderRepository) {

    operator fun invoke(req: GetOrderDetailRequest): GetOrderDetailResponse {
        val order = orderRepository.findById(req.id)
            ?: throw DomainException("Order ${req.id} not found")

        return GetOrderDetailResponse(
            id = order.id,
            lines = emptyList(),
            status = order.status, //TODO
            createdAt = "order.createdAt",//TODO
            total = order.total,
            customerId = order.customer.id
        )
    }

}

data class GetOrderDetailRequest(val id: String)

data class GetOrderDetailResponse(
    val id: String,
    val lines: List<OrderLine>,
    val customerId: String,
    val status: String,
    val createdAt: String,
    val total: Int,
) {
    data class OrderLine(
        val id: String,
        val createdAt: String,
        val total: Int,
    )
}