package es.urjc.realfood.restaurants.application.getrestaurants.dtos

data class BusinessHour(
    val day: Int,
    val ranges: List<Range>,
)