package es.urjc.realfood.restaurants.domain

import es.urjc.realfood.restaurants.domain.menu.Menu
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Embedded
import javax.persistence.Id

class Restaurant(
    val id: String,
    var name: String,
    var address: Address,
    var businessHours: BusinessHours,
    category: Category,
    val menu: Menu,
) {
    private var category: Int = category.id

    fun category() = Category.fromId(category)!!

    fun updateDetails(
        name: String,
        address: Address,
        businessHours: BusinessHours,
        category: Category
    ) {
        this.name = name
        this.address = address
        this.businessHours = businessHours
        this.category = category.id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Restaurant

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}