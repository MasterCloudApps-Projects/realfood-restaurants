package es.urjc.realfood.restaurants.application.getrestaurants.dtos

data class Address(
    val streetName: String,
    val number: Int,
    val city: String,
    val zipCode: String,
    val additional: String,
)