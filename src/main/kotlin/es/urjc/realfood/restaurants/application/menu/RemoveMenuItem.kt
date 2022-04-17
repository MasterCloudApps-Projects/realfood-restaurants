package es.urjc.realfood.restaurants.application.menu

import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RemoveMenuItem(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(request: RemoveMenuItemRequest): RemoveMenuItemResponse {
        val restaurant = restaurantRepository.findById(request.restaurantId)
            ?: throw DomainException("Restaurant ${request.restaurantId} not found")
        restaurant.menu.getBlock(request.blockId).removeItem(request.menuItemId)
        restaurantRepository.save(restaurant)
        return RemoveMenuItemResponse()
    }

}

data class RemoveMenuItemRequest(
    val restaurantId: String,
    val blockId: String,
    val menuItemId: String,
)

class RemoveMenuItemResponse