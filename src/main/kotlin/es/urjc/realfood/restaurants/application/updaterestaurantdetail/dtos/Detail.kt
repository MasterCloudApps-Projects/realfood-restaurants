package es.urjc.realfood.restaurants.application.updaterestaurantdetail.dtos

data class Detail(
    val name: String,
    val address: Address,
    val businessHours: List<BusinessHour>,
    val categoryId: Int,
)