package es.urjc.realfood.restaurants.application.createrestaurant

data class CreateRestaurantRequest(
    val name: String,
    val address: Address,
    val businessHours: List<BusinessHour>,
    val categoryId: Int,
    val menu: Menu,
) {

    data class Address(
        val streetname: String,
        val number: Int,
        val city: String,
        val zipCode: String,
        val additional: String,
    )

    data class BusinessHour(
        val day: Int,
        val ranges: List<Range>,
    )

    data class MenuItem(
        val name: String,
        val price: Int,
    )

    data class Menu(
        val items: List<MenuItem>,
    )

    data class Range(
        val from: String,
        val to: String,
    )
}