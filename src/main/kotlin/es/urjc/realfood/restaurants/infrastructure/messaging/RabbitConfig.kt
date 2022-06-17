package es.urjc.realfood.restaurants.infrastructure.messaging

import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Queue
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class RabbitConfig(private val amqpAdmin: AmqpAdmin) {

    @PostConstruct
    fun setup() {
        amqpAdmin.declareQueue(Queue("prepare-order"))
        amqpAdmin.declareQueue(Queue("prepared-orders"))
    }
}