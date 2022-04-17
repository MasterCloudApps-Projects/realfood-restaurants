package es.urjc.realfood.restaurants.domain.menu

class MenuBlock(
    val id: String,
    val name: String,
    var items: List<MenuItem>
) {
    fun addItem(menuItem: MenuItem) {
        this.items += menuItem
    }

    fun removeItem(menuItemId: String) {
        this.items = this.items.filterNot { it.id == menuItemId }
    }
}