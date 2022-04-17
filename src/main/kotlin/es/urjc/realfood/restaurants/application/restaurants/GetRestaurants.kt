package es.urjc.realfood.restaurants.application.restaurants

import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetRestaurants(private val restaurantsRepository: RestaurantRepository) {

    operator fun invoke(request: GetRestaurantsRequest): GetRestaurantsResponse {
        return GetRestaurantsResponse(
            items = restaurantsRepository.toList().map {
                GetRestaurantsResponse.Restaurant(
                    id = it.id,
                    name = it.name,
                    address = GetRestaurantsResponse.Address(
                        streetName = it.address.streetName,
                        number = it.address.number,
                        city = it.address.city,
                        zipCode = it.address.zipCode,
                        additional = it.address.additional
                    ),
                    businessHours = it.businessHours.hours.map { GetRestaurantsResponse.BusinessHour(day = it.day.value,
                        ranges = it.ranges.map {
                            GetRestaurantsResponse.Range(from = it.from.toString(),
                                to = it.to.toString())
                        })
                    },
                    categoryId = it.category().id
                )
            }
        )
    }

}

class GetRestaurantsRequest

data class GetRestaurantsResponse(
    val items: List<Restaurant>,
) {
    data class Restaurant(
        val id: String,
        val name: String,
        val address: Address,
        val businessHours: List<BusinessHour>,
        val categoryId: Int,
    )

    data class BusinessHour(
        val day: Int,
        val ranges: List<Range>,
    )

    data class Range(
        val from: String,
        val to: String,
    )

    data class Address(
        val streetName: String,
        val number: Int,
        val city: String,
        val zipCode: String,
        val additional: String,
    )
}

