package es.urjc.realfood.restaurants.application.updaterestaurantdetail.dtos

data class BusinessHour(
    val day: Int,
    val ranges: List<Range>,
)