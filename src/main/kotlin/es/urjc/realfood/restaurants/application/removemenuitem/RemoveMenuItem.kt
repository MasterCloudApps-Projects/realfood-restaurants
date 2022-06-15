package es.urjc.realfood.restaurants.application.removemenuitem

import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RemoveMenuItem(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(request: RemoveMenuItemRequest) {
        val restaurant = restaurantRepository.findById(request.restaurantId)
            ?: throw RestaurantNotFoundException("Restaurant ${request.restaurantId} not found")
        restaurant.menu.removeItem(request.menuItemId)
        restaurantRepository.save(restaurant)
    }

}