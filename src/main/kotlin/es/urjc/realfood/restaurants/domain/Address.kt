@file:Suppress("JpaAttributeMemberSignatureInspection")

package es.urjc.realfood.restaurants.domain

data class Address(
    val streetName: String,
    val number: Int,
    val city: String,
    val zipCode: String,
    val additional: String
)