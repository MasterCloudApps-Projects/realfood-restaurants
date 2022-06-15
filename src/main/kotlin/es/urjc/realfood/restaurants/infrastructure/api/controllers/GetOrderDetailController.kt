package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetail
import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetailRequest
import es.urjc.realfood.restaurants.application.getorderdetail.GetOrderDetailResponse
import es.urjc.realfood.restaurants.application.getrestaurants.GetRestaurantsRequest
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