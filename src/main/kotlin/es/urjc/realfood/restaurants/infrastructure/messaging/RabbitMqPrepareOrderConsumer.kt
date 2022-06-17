package es.urjc.realfood.restaurants.infrastructure.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.restaurants.application.updateorder.UpdateOrder
import es.urjc.realfood.restaurants.application.updateorder.UpdateOrderDetailRequest
import es.urjc.realfood.restaurants.domain.orders.OrderStatus
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class RabbitMqPrepareOrderConsumer(
    private val updateOrder: UpdateOrder,
) {

    private val objectMapper = ObjectMapper()
        .findAndRegisterModules()

    @RabbitListener(queues = ["prepare-order"], ackMode = "AUTO")
    private fun consume(message: String) {
        val prepareOrderRequest = objectMapper.readValue(message, PrepareOrderEvent::class.java)

        LOGGER.info(
            "Prepare order request from client '{}' and order '{}'",
            prepareOrderRequest.clientId,
            prepareOrderRequest.orderId
        )

        updateOrder(
            UpdateOrderDetailRequest(
                clientId = prepareOrderRequest.clientId,
                orderId = prepareOrderRequest.orderId,
                newStatus = OrderStatus.PREPARED.id
            )
        )
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RabbitMqPrepareOrderConsumer::class.java)
    }
}

data class PrepareOrderEvent(
    val clientId: String,
    val orderId: String,
)
