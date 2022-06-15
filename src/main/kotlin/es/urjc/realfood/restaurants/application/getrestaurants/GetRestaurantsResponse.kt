package es.urjc.realfood.restaurants.application.getrestaurants

import es.urjc.realfood.restaurants.application.getrestaurants.dtos.Restaurant

data class GetRestaurantsResponse(
    val items: List<Restaurant>,
)

