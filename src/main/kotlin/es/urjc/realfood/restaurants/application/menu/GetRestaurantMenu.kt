package es.urjc.realfood.restaurants.application.menu

import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.menu.*
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class GetRestaurantMenu(private val restaurantsRepository: RestaurantRepository) {

    operator fun invoke(request: GetRestaurantMenuRequest): GetRestaurantMenuResponse {
        val restaurant = restaurantsRepository.findById(request.id)
            ?: throw DomainException("Restaurant ${request.id} not found")
        return GetRestaurantMenuResponse(
            id = restaurant.menu.id,
            blocks = map(restaurant.menu.blocks)
        )
    }

    private fun map(blocks: List<MenuBlock>): List<GetRestaurantMenuResponse.MenuBlock> {
        return blocks.map {
            GetRestaurantMenuResponse.MenuBlock(
                id = it.id,
                name = it.name,
                items = mapItems(it.items)
            )
        }
    }

    private fun mapItems(items: List<MenuItem>): List<GetRestaurantMenuResponse.MenuItem> {
        return items.map {
            GetRestaurantMenuResponse.MenuItem(
                id = it.id,
                name = it.name,
                price = it.price,
                items = mapItems(it.items ?: emptyList()),
                extras = it.extras?.let { mapExtras(it) },
                components = it.components?.let { mapComponents(it) },
                variants = it.variants?.let { mapVariants(it) },
            )
        }
    }

    private fun mapVariants(variants: List<Variant>): List<GetRestaurantMenuResponse.Variant> {
        return variants.map {
            GetRestaurantMenuResponse.Variant(name = it.name)
        }
    }

    private fun mapComponents(components: List<Component>): List<GetRestaurantMenuResponse.Component> {
        return components.map {
            GetRestaurantMenuResponse.Component(name = it.name)
        }
    }

    private fun mapExtras(extras: List<Extra>): List<GetRestaurantMenuResponse.Extra> {
        return extras.map {
            GetRestaurantMenuResponse.Extra(price = it.price, name = it.name)
        }
    }

}

data class GetRestaurantMenuRequest(val id: String)

data class GetRestaurantMenuResponse(
    val id: String,
    val blocks: List<MenuBlock>,
) {

    data class MenuBlock(
        val id: String,
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
        val id: String,
        val name: String,
        val price: Int,
        val items: List<MenuItem>?,
        val extras: List<Extra>?,
        val components: List<Component>?,
        val variants: List<Variant>?,
    )
}

