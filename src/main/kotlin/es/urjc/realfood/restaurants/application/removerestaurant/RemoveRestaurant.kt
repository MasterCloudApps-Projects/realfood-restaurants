package es.urjc.realfood.restaurants.application.removerestaurant

import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RemoveRestaurant(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(req: RemoveRestaurantRequest) {
        val restaurant = restaurantRepository.findById(req.restaurantId)
            ?: throw RestaurantNotFoundException("Restaurant ${req.restaurantId} not found")
        restaurantRepository.remove(restaurant)
    }

}