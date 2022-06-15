package es.urjc.realfood.restaurants.application.getrestaurants

import es.urjc.realfood.restaurants.application.getrestaurants.dtos.Address
import es.urjc.realfood.restaurants.application.getrestaurants.dtos.BusinessHour
import es.urjc.realfood.restaurants.application.getrestaurants.dtos.Range
import es.urjc.realfood.restaurants.application.getrestaurants.dtos.Restaurant
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetRestaurants(private val restaurantsRepository: RestaurantRepository) {

    operator fun invoke(): GetRestaurantsResponse {
        return GetRestaurantsResponse(
            items = restaurantsRepository.toList().map {
                Restaurant(
                    id = it.id,
                    name = it.name,
                    address = Address(
                        streetName = it.address.streetName,
                        number = it.address.number,
                        city = it.address.city,
                        zipCode = it.address.zipCode,
                        additional = it.address.additional
                    ),
                    businessHours = it.businessHours.hours.map { BusinessHour(day = it.day.value,
                        ranges = it.ranges.map {
                            Range(from = it.from.toString(),
                                to = it.to.toString())
                        })
                    },
                    categoryId = it.category().id
                )
            }
        )
    }

}

