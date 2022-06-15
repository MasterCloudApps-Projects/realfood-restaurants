package es.urjc.realfood.restaurants.domain.orders

data class OrderLine(
    val itemId: String,
    val price: Int,
    val qty: Int,
) {
    fun total(): Int = qty * price
}

data class OrderLineExtra(
    val name: String,
    val price: Int,
) {
    fun total(): Int {
        return price
    }
}