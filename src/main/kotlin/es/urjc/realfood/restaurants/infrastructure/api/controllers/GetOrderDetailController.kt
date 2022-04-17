package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.orders.GetOrderDetail
import es.urjc.realfood.restaurants.application.orders.GetOrderDetailRequest
import es.urjc.realfood.restaurants.application.orders.GetOrderDetailResponse
import es.urjc.realfood.restaurants.application.restaurants.GetRestaurants
import es.urjc.realfood.restaurants.application.restaurants.GetRestaurantsRequest
import es.urjc.realfood.restaurants.application.restaurants.GetRestaurantsResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GetOrderDetailController(private val getOrderDetail: GetOrderDetail) {

    @GetMapping("/api/orders/{orderId}")
    fun getOrderDetail(@RequestBody request: GetRestaurantsRequest, @PathVariable orderId: String): GetOrderDetailResponse =
        getOrderDetail(GetOrderDetailRequest(id = orderId))

}

// Delivery servicelinkedin tlinkedin 