package es.urjc.realfood.restaurants.application.menu

import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.menu.Component
import es.urjc.realfood.restaurants.domain.menu.Extra
import es.urjc.realfood.restaurants.domain.menu.MenuItem
import es.urjc.realfood.restaurants.domain.menu.Variant
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
class AddMenuItem(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(request: AddMenuItemRequest): AddMenuItemResponse {
        val restaurant = restaurantRepository.findById(request.restaurantId)
            ?: throw DomainException("Restaurant ${request.restaurantId} not found")
        val menuItem = mapMenuItem(request.menuItem)
        restaurant.menu.getBlock(request.blockId).addItem(menuItem)
        restaurantRepository.save(restaurant)
        return AddMenuItemResponse(itemId = menuItem.id)
    }

    private fun mapMenuItem(menuItem: AddMenuItemRequest.MenuItem): MenuItem {
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

    private fun mapExtra(extra: AddMenuItemRequest.Extra): Extra {
        return Extra(
            id = UUID.randomUUID().toString(),
            price = extra.price,
            name = extra.name
        )
    }

    private fun mapComponent(component: AddMenuItemRequest.Component): Component {
        return Component(
            id = UUID.randomUUID().toString(),
            name = component.name
        )
    }

    private fun mapVariant(variant: AddMenuItemRequest.Variant): Variant {
        return Variant(
            id = UUID.randomUUID().toString(),
            name = variant.name
        )
    }

}

data class AddMenuItemRequest(
    val restaurantId: String,
    val blockId: String,
    val menuItem: MenuItem,
) {
    data class MenuItem(
        val name: String,
        val price: Int,
        val items: List<MenuItem>?,
        val extras: List<Extra>?,
        val components: List<Component>?,
        val variants: List<Variant>?,
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
}

data class AddMenuItemResponse(
    val itemId: String,
)