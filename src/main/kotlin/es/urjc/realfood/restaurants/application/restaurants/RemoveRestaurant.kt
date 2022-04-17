package es.urjc.realfood.restaurants.application.restaurants

import es.urjc.realfood.restaurants.domain.exceptions.DomainException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class RemoveRestaurant(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(req: RemoveRestaurantRequest) : RemoveRestaurantResponse {
        val restaurant = restaurantRepository.findById(req.restaurantId)
            ?: throw DomainException("Restaurant ${req.restaurantId} not found")
        restaurantRepository.remove(restaurant)
        return RemoveRestaurantResponse()
    }

}

data class RemoveRestaurantRequest(
    val restaurantId: String,
)

class RemoveRestaurantResponse