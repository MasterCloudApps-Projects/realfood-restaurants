package es.urjc.realfood.restaurants.application.getrestaurants.dtos

data class Restaurant(
    val id: String,
    val name: String,
    val address: Address,
    val businessHours: List<BusinessHour>,
    val categoryId: Int,
)