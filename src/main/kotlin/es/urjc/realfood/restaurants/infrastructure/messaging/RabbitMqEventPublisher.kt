package es.urjc.realfood.restaurants.infrastructure.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.restaurants.domain.event.DomainEvent
import es.urjc.realfood.restaurants.domain.event.EventPublisher
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class RabbitMqEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) : EventPublisher {

    private val objectMapper = ObjectMapper()
        .findAndRegisterModules()

    private val queue: String = "prepared-orders"

    override fun publish(event: DomainEvent) {
        val msg: String = objectMapper.writeValueAsString(event)
        rabbitTemplate.convertAndSend(queue, event::class.java.simpleName, msg)
        LOGGER.info("[Publisher] Event sent to queue '{}': {}", queue, msg)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RabbitMqEventPublisher::class.java)
    }
}