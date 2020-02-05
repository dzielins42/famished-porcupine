package pl.dzielins42.famishedporcupine.data.source.room

import androidx.room.Embedded
import androidx.room.Relation

data class ShelfSection(
    @Embedded val definition: ProductDefinition,
    @Relation(
        parentColumn = "id",
        entityColumn = "definitionId"
    )
    val products: List<ShelfProduct>
)