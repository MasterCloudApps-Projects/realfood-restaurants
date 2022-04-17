package es.urjc.realfood.restaurants.domain.menu

class Menu(
    val id: String,
    val blocks: List<MenuBlock>,
) {
    fun findItem(itemId: String): MenuItem? {
        return blocks
            .map { it.items }
            .asSequence()
            .flatten()
            .find { it.id == itemId }
    }

    fun getBlock(blockId: String): MenuBlock {
        return blocks.first { it.id == blockId }
    }
}