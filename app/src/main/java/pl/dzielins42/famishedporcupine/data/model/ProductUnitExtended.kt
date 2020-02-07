package pl.dzielins42.famishedporcupine.data.model

import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import java.util.*

data class ProductUnitExtended(
    private val internalProductUnit: ProductUnit,
    val freshness: Freshness
) {
    val id: Long
        get() = internalProductUnit.id
    val definitionId: Long
        get() = internalProductUnit.definitionId
    val expirationDate: Date
        get() = internalProductUnit.expirationDate
}