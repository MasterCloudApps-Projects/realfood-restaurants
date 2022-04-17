package es.urjc.realfood.restaurants.domain.orders

data class OrderLine(
    val itemId: String,
    val price: Int,
    val qty: Int,
    val variant: String?,
    val extras: List<OrderLineExtra>,
    val components: List<String>,
) {
    fun total(): Int = (qty * price) + extras.sumOf { it.total() }
}

data class OrderLineExtra(
    val name: String,
    val price: Int,
) {
    fun total(): Int {
        return price
    }
}