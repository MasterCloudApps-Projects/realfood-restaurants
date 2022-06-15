package es.urjc.realfood.restaurants.domain

enum class Category(val id: Int) {
    ASIAN(1),
    MEDITERRANEAN(2),
    AMERICAN(3),
    LATIN(4);
    companion object {
        fun fromId(id: Int) : Category? {
            return values().firstOrNull { it.id == id }
        }
    }
}