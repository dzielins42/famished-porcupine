package pl.dzielins42.famishedporcupine.data.source.room

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "product_units",
    foreignKeys = [
        ForeignKey(
            entity = ProductDefinition::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("definitionId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductUnit(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val definitionId: Long,
    val expirationDate: Date
)