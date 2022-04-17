package es.urjc.realfood.restaurants.infrastructure.data.entities

data class MongoMenuBlock(
    val id: String,
    val name: String,
    val items: List<MongoMenuItem>
)