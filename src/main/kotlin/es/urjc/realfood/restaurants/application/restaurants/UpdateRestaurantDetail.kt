package es.urjc.realfood.restaurants.application.restaurants

import es.urjc.realfood.restaurants.domain.*
import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalTime
import javax.transaction.Transactional

@Service
@Transactional
class UpdateRestaurantDetail(
    private val restaurantRepository: RestaurantRepository,
) {

    operator fun invoke(req: UpdateRestaurantDetailRequest): UpdateRestaurantDetailResponse {
        val restaurant = restaurantRepository.findById(req.restaurantId)?: throw DomainException("Restaurant ${req.restaurantId} not found")
        restaurant.updateDetails(
            name = req.detail.name,
            address = mapAddress(req.detail.address),
            businessHours = mapBusinessHours(req),
            category = mapCategory(req)
        )
        restaurantRepository.save(restaurant)
        return UpdateRestaurantDetailResponse()
    }

    private fun mapCategory(req: UpdateRestaurantDetailRequest): Category {
        return Category.fromId(req.detail.categoryId) ?: throw DomainException("Category ${req.detail.categoryId} not found")
    }

    private fun mapAddress(address: UpdateRestaurantDetailRequest.Address): Address {
        return Address(
            streetName = address.streetname,
            number = address.number,
            city = address.city,
            zipCode = address.zipCode,
            additional = address.additional
        )
    }

    private fun mapBusinessHours(req: UpdateRestaurantDetailRequest): BusinessHours {
        return BusinessHours(hours = req.detail.businessHours.map {
            BusinessHour(
                day = DayOfWeek.of(it.day),
                ranges = it.ranges.map {
                    Range(
                        from = LocalTime.parse(it.from),
                        to = LocalTime.parse(it.to)
                    )
                }
            )
        })
    }
}

data class UpdateRestaurantDetailRequest(
    val restaurantId: String,
    val detail: Detail,
) {

    data class Detail(
        val name: String,
        val address: Address,
        val businessHours: List<BusinessHour>,
        val categoryId: Int,
    )

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

    data class Range(
        val from: String,
        val to: String,
    )
}

class UpdateRestaurantDetailResponse