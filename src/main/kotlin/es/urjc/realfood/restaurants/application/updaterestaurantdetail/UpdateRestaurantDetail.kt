package es.urjc.realfood.restaurants.application.updaterestaurantdetail

import es.urjc.realfood.restaurants.application.updaterestaurantdetail.dtos.Address
import es.urjc.realfood.restaurants.domain.Category
import es.urjc.realfood.restaurants.domain.Address as DomainAddress
import es.urjc.realfood.restaurants.domain.BusinessHours as DomainBusinessHours
import es.urjc.realfood.restaurants.domain.BusinessHour as DomainBusinessHour
import es.urjc.realfood.restaurants.domain.Range as DomainRange
import es.urjc.realfood.restaurants.domain.exceptions.CategoryNotFoundException
import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
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

    operator fun invoke(req: UpdateRestaurantDetailRequest) {
        val restaurant = restaurantRepository.findById(req.restaurantId)
            ?: throw RestaurantNotFoundException("Restaurant ${req.restaurantId} not found")
        restaurant.updateDetails(
            name = req.detail.name,
            address = mapAddress(req.detail.address),
            businessHours = mapBusinessHours(req),
            category = mapCategory(req)
        )
        restaurantRepository.save(restaurant)
    }

    private fun mapCategory(req: UpdateRestaurantDetailRequest): Category {
        return Category.fromId(req.detail.categoryId)
            ?: throw CategoryNotFoundException("Category ${req.detail.categoryId} not found")
    }

    private fun mapAddress(address: Address): DomainAddress {
        return DomainAddress(
            streetName = address.streetName,
            number = address.number,
            city = address.city,
            zipCode = address.zipCode,
            additional = address.additional
        )
    }

    private fun mapBusinessHours(req: UpdateRestaurantDetailRequest): DomainBusinessHours {
        return DomainBusinessHours(hours = req.detail.businessHours.map {
            DomainBusinessHour(
                day = DayOfWeek.of(it.day),
                ranges = it.ranges.map {
                    DomainRange(
                        from = LocalTime.parse(it.from),
                        to = LocalTime.parse(it.to)
                    )
                }
            )
        })
    }
}