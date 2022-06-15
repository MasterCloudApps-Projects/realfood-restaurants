package es.urjc.realfood.restaurants.domain.menu

class Menu(
    val id: String,
    items: List<MenuItem>,
) {

    private val items: MutableList<MenuItem> = items.toMutableList()
    fun findItem(itemId: String): MenuItem? {
        return items
            .find { it.id == itemId }
    }

    fun removeItem(menuItemId: String) {
        items
            .removeIf { it.id == menuItemId }
    }

    fun items(): List<MenuItem> {
        return items.toList()
    }

    fun addItem(menuItem: MenuItem) {
        items.add(menuItem)
    }
}