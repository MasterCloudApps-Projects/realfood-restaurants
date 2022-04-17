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
        jpaRestaurantRepository.save(JpaRestaurant(
            id = restaurant.id,
            name = restaurant.name,
            streetName = restaurant.address.streetName,
            number = restaurant.address.number,
            city = restaurant.address.city,
            zipCode = restaurant.address.zipCode,
            additional = restaurant.address.additional,
            businessHours = toJson(restaurant.businessHours),
            categoryId = restaurant.category().id
        ))
        mongoMenuRepository.save(MongoMenu(
            id = restaurant.menu.id,
            restaurantId = restaurant.id,
            blocks = mapBlocks(restaurant.menu.blocks, restaurantId = restaurant.id)
        ))
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

    private fun mapMongoBlocks(blocks: List<MongoMenuBlock>): List<MenuBlock> {
        return blocks.map { MenuBlock(name = it.name, items = mapMongoItems(it.items), id = it.id) }
    }

    private fun mapMongoVariants(variants: List<MongoMenuVariant>): List<Variant> {
        return variants.map {
            Variant(name = it.name, id = it.id)
        }
    }

    private fun mapMongoComponents(components: List<MongoMenuComponent>): List<Component> {
        return components.map {
            Component(id = it.id, name = it.name)
        }
    }

    private fun mapMongoExtras(extras: List<MongoMenuExtra>): List<Extra> {
        return extras.map {
            Extra(id = it.id, name = it.name, price = it.price)
        }
    }

    private fun mapToDomain(restaurant: JpaRestaurant, mongoMenu: MongoMenu): Restaurant {
        return Restaurant(
            id = restaurant.id,
            name = restaurant.name,
            address = Address(streetName = restaurant.streetName,
                number = restaurant.number,
                city = restaurant.city,
                zipCode = restaurant.zipCode,
                additional = restaurant.additional),
            businessHours = fromJson(restaurant.businessHours),
            category = Category.fromId(restaurant.categoryId)!!,
            menu = mongoMenu.let { Menu(id = it.id, blocks = mapMongoBlocks(it.blocks)) }
        )
    }

    private fun mapMongoItems(items: List<MongoMenuItem>): List<MenuItem> {
        return items.map {
            MenuItem(
                name = it.name,
                price = it.price,
                items = mapMongoItems(it.items),
                id = it.id,
                extras = mapMongoExtras(it.extras),
                components = mapMongoComponents(it.components),
                variants = mapMongoVariants(it.variants),
            )
        }
    }

    private fun mapBlocks(menu: List<MenuBlock>, restaurantId: String): List<MongoMenuBlock> {
        return menu.map {
            MongoMenuBlock(
                id = it.id,
                name = it.name,
                items = map(it.items,
                    restaurantId).also {
                    mongoMenuItemRegistryRepository.saveAll(it.map {
                        MongoMenuItemRestaurant(it.id,
                            it.restaurantId)
                    })
                }
            )
        }
    }

    private fun map(items: List<MenuItem>, restaurantId: String): List<MongoMenuItem> {
        return items.map {
            MongoMenuItem(
                id = it.id,
                name = it.name,
                price = it.price,
                items = map(it.items ?: emptyList(), restaurantId),
                extras = mapExtras(it.extras ?: emptyList()),
                components = mapComponents(it.components ?: emptyList()),
                variants = mapVariants(it.variants ?: emptyList()),
                restaurantId = restaurantId
            )
        }
    }

    private fun mapVariants(variants: List<Variant>): List<MongoMenuVariant> {
        return variants.map {
            MongoMenuVariant(id = it.id, it.name)
        }
    }

    private fun mapComponents(components: List<Component>): List<MongoMenuComponent> {
        return components.map {
            MongoMenuComponent(id = it.id, name = it.name)
        }
    }

    private fun mapExtras(extras: List<Extra>): List<MongoMenuExtra> {
        return extras.map {
            MongoMenuExtra(
                id = it.id,
                price = it.price,
                name = it.name
            )
        }
    }

    private fun toJson(hours: BusinessHours) = mapper.writeValueAsString(hours)

}