package es.urjc.realfood.restaurants.infrastructure.data.services

import com.fasterxml.jackson.databind.ObjectMapper
import es.urjc.realfood.restaurants.domain.Address
import es.urjc.realfood.restaurants.domain.BusinessHours
import es.urjc.realfood.restaurants.domain.Category
import es.urjc.realfood.restaurants.domain.Restaurant
import es.urjc.realfood.restaurants.domain.menu.*
import es.urjc.realfood.restaurants.domain.services.RestaurantRepository
import es.urjc.realfood.restaurants.infrastructure.data.entities.*
import es.urjc.realfood.restaurants.infrastructure.data.exception.DataAccessException
import org.springframework.stereotype.Repository

@Repository
class DatabaseRestaurantRepository(
    private val jpaRestaurantRepository: JpaRestaurantRepository,
    private val mongoMenuRepository: MongoMenuRepository,
    private val mongoMenuItemRegistryRepository: MongoMenuItemRegistryRepository,
) : RestaurantRepository {

    private val mapper = ObjectMapper()
        .findAndRegisterModules()

    override fun save(restaurant: Restaurant) {
        jpaRestaurantRepository.save(
            JpaRestaurant(
                id = restaurant.id,
                name = restaurant.name,
                streetName = restaurant.address.streetName,
                number = restaurant.address.number,
                city = restaurant.address.city,
                zipCode = restaurant.address.zipCode,
                additional = restaurant.address.additional,
                businessHours = toJson(restaurant.businessHours),
                categoryId = restaurant.category().id
            )
        )

        val menuItems = map(items = restaurant.menu.items(), restaurantId = restaurant.id).also {
            mongoMenuItemRegistryRepository.saveAll(it.map {
                MongoMenuItemRestaurant(
                    it.id,
                    it.restaurantId
                )
            })
        }

        mongoMenuRepository.save(
            MongoMenu(
                id = restaurant.menu.id,
                restaurantId = restaurant.id,
                items = menuItems
            )
        )
    }

    override fun toList(): List<Restaurant> {
        val restaurantMenus: Map<String, List<MongoMenu>> = mongoMenuRepository.findAll().groupBy { it.restaurantId }
        return jpaRestaurantRepository.findAll().map { mapToDomain(it, restaurantMenus[it.id]!!.first()) }
    }

    override fun findById(id: String): Restaurant {
        val jpaRestaurant =
            jpaRestaurantRepository.findById(id).orElseThrow { DataAccessException("Restaurant '$id' not found") }
        val mongoMenu = mongoMenuRepository.findMongoMenuByRestaurantId(id)
        return mapToDomain(jpaRestaurant, mongoMenu)
    }

    override fun findByItemId(itemId: String): Restaurant? {
        return mongoMenuItemRegistryRepository.findByMenuItemId(itemId)
            ?.let { findById(it.restaurantId) }
    }

    override fun remove(restaurant: Restaurant) {
        jpaRestaurantRepository.findById(restaurant.id).ifPresent { jpaRestaurantRepository.delete(it) }
        mongoMenuItemRegistryRepository.deleteByRestaurantId(restaurant.id)
        mongoMenuRepository.deleteByRestaurantId(restaurant.id)
    }

    private fun fromJson(businessHours: String): BusinessHours =
        mapper.readValue(businessHours, BusinessHours::class.java)

    private fun mapToDomain(restaurant: JpaRestaurant, mongoMenu: MongoMenu): Restaurant {
        return Restaurant(
            id = restaurant.id,
            name = restaurant.name,
            address = Address(
                streetName = restaurant.streetName,
                number = restaurant.number,
                city = restaurant.city,
                zipCode = restaurant.zipCode,
                additional = restaurant.additional
            ),
            businessHours = fromJson(restaurant.businessHours),
            category = Category.fromId(restaurant.categoryId)!!,
            menu = mongoMenu.let { Menu(id = it.id, items = mapMongoItems(it.items)) }
        )
    }

    private fun mapMongoItems(items: List<MongoMenuItem>): List<MenuItem> {
        return items.map {
            MenuItem(
                name = it.name,
                price = it.price,
                id = it.id,
            )
        }
    }

    private fun map(items: List<MenuItem>, restaurantId: String): List<MongoMenuItem> {
        return items.map {
            MongoMenuItem(
                id = it.id,
                name = it.name,
                price = it.price,
                restaurantId = restaurantId
            )
        }
    }

    private fun toJson(hours: BusinessHours) = mapper.writeValueAsString(hours)

}