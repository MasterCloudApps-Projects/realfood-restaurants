package es.urjc.realfood.restaurants.infrastructure.data.entities

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class MongoMenu(
    @Id val id: String,
    val restaurantId: String,
    val blocks: List<MongoMenuBlock>
)