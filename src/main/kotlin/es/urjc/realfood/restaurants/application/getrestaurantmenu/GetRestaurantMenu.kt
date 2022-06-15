package es.urjc.realfood.restaurants.application.getrestaurantmenu

import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

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

    private fun mapItems(items: List<es.urjc.realfood.restaurants.domain.menu.MenuItem>):
            List<GetRestaurantMenuResponse.MenuItem> {
        return items.map {
            GetRestaurantMenuResponse.MenuItem(
                id = it.id,
                name = it.name,
                price = it.price,
            )
        }
    }

}

