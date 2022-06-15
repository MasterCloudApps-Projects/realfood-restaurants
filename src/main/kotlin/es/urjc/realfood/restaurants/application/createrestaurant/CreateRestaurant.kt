package es.urjc.realfood.restaurants.application.createrestaurant

import es.urjc.realfood.restaurants.domain.*
import es.urjc.realfood.restaurants.domain.exceptions.CategoryNotFoundException
import es.urjc.realfood.restaurants.domain.menu.Menu
import es.urjc.realfood.restaurants.domain.menu.MenuItem
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class CreateRestaurant(
    private val restaurantRepository: RestaurantRepository,
) {

    operator fun invoke(req: CreateRestaurantRequest): CreateRestaurantResponse {
        val newRestaurant = Restaurant(
            id = UUID.randomUUID().toString(),
            name = req.name,
            address = mapAddress(req.address),
            businessHours = mapBusinessHours(req),
            category = mapCategory(req),
            menu = mapMenu(req)
        )
        restaurantRepository.save(newRestaurant)
        return CreateRestaurantResponse(id = newRestaurant.id)
    }

    private fun mapCategory(req: CreateRestaurantRequest): Category {
        return Category.fromId(req.categoryId) ?: throw CategoryNotFoundException("Category ${req.categoryId} not found")
    }

    private fun mapAddress(address: CreateRestaurantRequest.Address): Address {
        return Address(
            streetName = address.streetname,
            number = address.number,
            city = address.city,
            zipCode = address.zipCode,
            additional = address.additional
        )
    }

    private fun mapBusinessHours(req: CreateRestaurantRequest): BusinessHours {
        return BusinessHours(hours = req.businessHours.map {
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

    private fun mapMenu(req: CreateRestaurantRequest): Menu {
        return Menu(
            id = UUID.randomUUID().toString(),
            items = req.menu.items.map { item -> mapMenuItem(item) }
        )
    }

    private fun mapMenuItem(menuItem: CreateRestaurantRequest.MenuItem): MenuItem {
        return MenuItem(
            id = UUID.randomUUID().toString(),
            name = menuItem.name,
            price = menuItem.price,
        )
    }
}

