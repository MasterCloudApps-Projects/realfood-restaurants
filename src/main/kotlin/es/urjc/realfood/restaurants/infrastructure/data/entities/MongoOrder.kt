package es.urjc.realfood.restaurants.infrastructure.data.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class MongoOrder(
    @Id val id: String,
    val restaurantId: String,
    val status: Int,
    val lines: List<MongoOrderLine>,
    val total: Int,
    val customerId: String,
    val createdAt: Date
)

data class MongoOrderLine(
    val itemId: String,
    val qty: Int,
    val price: Int,
)

data class MongoOrderLineExtra(
    val name: String,
    val price: Int,
)