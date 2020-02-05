package pl.dzielins42.famishedporcupine.data.source.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "shelf_products",
    foreignKeys = [
        ForeignKey(
            entity = ProductDefinition::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("definitionId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShelfProduct(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val definitionId: String,
    val expirationData: Date
)