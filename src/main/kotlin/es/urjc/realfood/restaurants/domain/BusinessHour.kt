package es.urjc.realfood.restaurants.domain

import java.time.DayOfWeek

data class BusinessHour(
    val day: DayOfWeek,
    val ranges: List<Range>,
)