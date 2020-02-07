package pl.dzielins42.famishedporcupine.data.model

import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf

data class ProductShelfExtended(
    private val internalProductShelf: ProductShelf,
    val units: List<ProductUnitExtended>
) {
    val definition: ProductDefinition
        get() = internalProductShelf.definition

    fun getProductsCountByFreshness(freshness: Freshness): Int {
        return units.filter { it.freshness == freshness }.size
    }
}