package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetail
import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetailRequest
import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetailResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
class GetOrderDetailController(private val getOrderDetail: GetOrderDetail) {

    @GetMapping("/api/orders/{orderId}")
    fun getOrderDetail(
        @PathVariable orderId: String,
        principal: Principal?,
    ): GetOrderDetailResponse =
        getOrderDetail(
            GetOrderDetailRequest(
                id = orderId,
                clientId = principal?.name ?: throw IllegalArgumentException("Client token must be provided")
            )
        )

}