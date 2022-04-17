package es.urjc.realfood.restaurants.application.restaurants

import es.urjc.realfood.restaurants.domain.*
import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.menu.*
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class RegisterNewRestaurant(
    private val restaurantRepository: RestaurantRepository,
) {

    operator fun invoke(req: RegisterNewRestaurantRequest): RegisterNewRestaurantResponse {
        val newRestaurant = Restaurant(
            id = UUID.randomUUID().toString(),
            name = req.name,
            address = mapAddress(req.address),
            businessHours = mapBusinessHours(req),
            category = mapCategory(req),
            menu = mapMenu(req)
        )
        restaurantRepository.save(newRestaurant)
        return RegisterNewRestaurantResponse(id = newRestaurant.id)
    }

    private fun mapCategory(req: RegisterNewRestaurantRequest): Category {
        return Category.fromId(req.categoryId) ?: throw DomainException("Category ${req.categoryId} not found")
    }

    private fun mapAddress(address: RegisterNewRestaurantRequest.Address): Address {
        return Address(
            streetName = address.streetname,
            number = address.number,
            city = address.city,
            zipCode = address.zipCode,
            additional = address.additional
        )
    }

    private fun mapBusinessHours(req: RegisterNewRestaurantRequest): BusinessHours {
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

    private fun mapMenu(req: RegisterNewRestaurantRequest): Menu {
        return Menu(
            id = UUID.randomUUID().toString(),
            blocks = req.menu.blocks.map { menuBlockDto -> mapMenuBlock(menuBlockDto) }
        )
    }

    private fun mapMenuBlock(menuBlockDto: RegisterNewRestaurantRequest.MenuBlock): MenuBlock {
        return MenuBlock(
            name = menuBlockDto.name,
            items = menuBlockDto.items.map { mapMenuItem(it) },
            id = UUID.randomUUID().toString()
        )
    }

    private fun mapMenuItem(menuItem: RegisterNewRestaurantRequest.MenuItem): MenuItem {
        return MenuItem(
            id = UUID.randomUUID().toString(),
            name = menuItem.name,
            price = menuItem.price,
            items = menuItem.items?.map { mapMenuItem(it) } ?: emptyList(),
            extras = menuItem.extras?.map { mapExtra(it) },
            components = menuItem.components?.map { mapComponent(it) },
            variants = menuItem.variants?.map { mapVariant(it) },
        )
    }

    private fun mapExtra(extra: RegisterNewRestaurantRequest.Extra): Extra {
        return Extra(
            id = UUID.randomUUID().toString(),
            price = extra.price,
            name = extra.name
        )
    }

    private fun mapComponent(component: RegisterNewRestaurantRequest.Component): Component {
        return Component(
            id = UUID.randomUUID().toString(),
            name = component.name
        )
    }

    private fun mapVariant(variant: RegisterNewRestaurantRequest.Variant): Variant {
        return Variant(
            id = UUID.randomUUID().toString(),
            name = variant.name
        )
    }

}

data class RegisterNewRestaurantRequest(
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

    data class MenuBlock(
        val name: String,
        val items: List<MenuItem>,
    )

    data class Component(
        val name: String,
    )

    data class Variant(
        val name: String,
    )

    data class Extra(
        val price: Int,
        val name: String,
    )

    data class MenuItem(
        val name: String,
        val price: Int,
        val items: List<MenuItem>?,
        val extras: List<Extra>?,
        val components: List<Component>?,
        val variants: List<Variant>?,
    )

    data class Menu(
        val blocks: List<MenuBlock>,
    )

    data class Range(
        val from: String,
        val to: String,
    )
}

data class RegisterNewRestaurantResponse(
    val id: String,
)