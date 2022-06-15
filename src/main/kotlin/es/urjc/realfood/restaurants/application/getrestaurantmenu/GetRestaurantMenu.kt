package es.urjc.realfood.restaurants.application.getrestaurantmenu

import es.urjc.realfood.restaurants.application.getrestaurantmenu.dto.MenuItem
import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import es.urjc.realfood.restaurants.domain.menu.MenuItem as DomainMenuItem

@Service
@Transactional
class GetRestaurantMenu(private val restaurantsRepository: RestaurantRepository) {

    operator fun invoke(request: GetRestaurantMenuRequest): GetRestaurantMenuResponse {
        val restaurant = restaurantsRepository.findById(request.id)
            ?: throw RestaurantNotFoundException("Restaurant ${request.id} not found")
        return GetRestaurantMenuResponse(
            id = restaurant.menu.id,
            items = mapItems(restaurant.menu.items())
        )
    }

    private fun mapItems(items: List<DomainMenuItem>):
            List<MenuItem> {
        return items.map {
            MenuItem(
                id = it.id,
                name = it.name,
                price = it.price,
            )
        }
    }

}

