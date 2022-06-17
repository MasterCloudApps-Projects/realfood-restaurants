package es.urjc.realfood.restaurants.domain.event

interface EventPublisher {
    fun publish(event: DomainEvent)
}