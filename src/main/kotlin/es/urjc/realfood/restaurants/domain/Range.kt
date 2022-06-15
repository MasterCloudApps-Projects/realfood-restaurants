package es.urjc.realfood.restaurants.domain

import java.time.LocalTime

data class Range(
    val from: LocalTime,
    val to: LocalTime
)