package es.urjc.realfood.restaurants.application.updaterestaurantdetail

import es.urjc.realfood.restaurants.application.updaterestaurantdetail.dtos.Detail

data class UpdateRestaurantDetailRequest(
    val restaurantId: String,
    val detail: Detail,
)