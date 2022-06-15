package es.urjc.realfood.restaurants.infrastructure.api.controllers

import es.urjc.realfood.restaurants.application.updaterestaurantdetail.UpdateRestaurantDetail
import es.urjc.realfood.restaurants.application.updaterestaurantdetail.UpdateRestaurantDetailRequest
import es.urjc.realfood.restaurants.application.updaterestaurantdetail.UpdateRestaurantDetailResponse
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateRestaurantDetailController(private val updateRestaurantDetail: UpdateRestaurantDetail) {

    @PatchMapping("/api/restaurants/{restaurantId}")
    fun postRegisterNewRestaurant(
        @PathVariable restaurantId: String,
        @RequestBody restaurantDetailRequest: UpdateRestaurantDetailRequest.Detail,
    ): UpdateRestaurantDetailResponse =
        updateRestaurantDetail(
            UpdateRestaurantDetailRequest(
            restaurantId = restaurantId,
            detail = restaurantDetailRequest
        )
        )
}