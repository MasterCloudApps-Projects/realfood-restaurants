package es.urjc.realfood.restaurants.application.addmenuitem

import es.urjc.realfood.restaurants.application.addmenuitem.dto.MenuItem
import es.urjc.realfood.restaurants.domain.exceptions.RestaurantNotFoundException
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import es.urjc.realfood.restaurants.domain.menu.MenuItem as DomainMenuItem

@Service
@Transactional
class AddMenuItem(private val restaurantRepository: RestaurantRepository) {

    operator fun invoke(request: AddMenuItemRequest): AddMenuItemResponse {
        val restaurant = restaurantRepository.findById(request.restaurantId)
            ?: throw RestaurantNotFoundException("Restaurant ${request.restaurantId} not found")
        val menuItem = mapMenuItem(request.menuItem)
        restaurant.menu.addItem(menuItem)
        restaurantRepository.save(restaurant)
        return AddMenuItemResponse(itemId = menuItem.id)
    }

    private fun mapMenuItem(menuItem: MenuItem): DomainMenuItem {
        return DomainMenuItem(
            id = UUID.randomUUID().toString(),
            name = menuItem.name,
            price = menuItem.price,
        )
    }
}