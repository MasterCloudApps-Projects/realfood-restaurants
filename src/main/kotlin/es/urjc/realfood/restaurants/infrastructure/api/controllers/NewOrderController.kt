package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.orders.NewOrder
import es.urjc.realfood.restaurants.application.orders.NewOrderRequest
import es.urjc.realfood.restaurants.application.orders.NewOrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NewOrderController(private val newOrder: NewOrder) {

    @PostMapping("/api/restaurants/orders")
    fun postNewOrder(@RequestBody request: NewOrderRequest) : NewOrderResponse =
        newOrder(request)

}