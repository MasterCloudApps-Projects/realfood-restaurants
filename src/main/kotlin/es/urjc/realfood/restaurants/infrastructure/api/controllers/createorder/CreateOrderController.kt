package es.urjc.realfood.restaurants.infrastructure.api.controllers.createorder

import es.urjc.realfood.restaurants.application.createorder.CreateOrder
import es.urjc.realfood.restaurants.application.createorder.CreateOrderRequest
import es.urjc.realfood.restaurants.application.createorder.CreateOrderResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import es.urjc.realfood.restaurants.infrastructure.api.controllers.createorder.CreateOrderRequest as HttpCreateOrderRequest

@RestController
class CreateOrderController(private val createOrder: CreateOrder) {

    @PostMapping("/api/orders")
    fun postCreateOrder(
        @RequestBody request: HttpCreateOrderRequest,
        principal: Principal?,
    ): CreateOrderResponse =
        createOrder(
            CreateOrderRequest(
                restaurantId = request.restaurantId,
                clientId = principal?.name ?: throw IllegalArgumentException("Client token must be provided"),
                lines = request.lines
            )
        )
}