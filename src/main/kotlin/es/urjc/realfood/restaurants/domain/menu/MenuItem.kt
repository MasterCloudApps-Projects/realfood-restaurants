package es.urjc.realfood.restaurants.domain.menu

class MenuItem(
    val id: String,
    val name: String,
    val price: Int,
    val items: List<MenuItem>?,
    val extras: List<Extra>?,
    val components: List<Component>?,
    val variants: List<Variant>?,
) {
    fun findVariant(id: String): Variant? {
        return variants?.find { it.id == id }
    }

    fun findComponent(id: String): Component? {
        return components?.find { it.id == id }
    }

    fun findExtra(id: String): Extra? {
        return extras?.find { it.id == id }
    }
}