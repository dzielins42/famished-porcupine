package pl.dzielins42.famishedporcupine.data.source.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_definitnions"
)
data class ProductDefinition(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
)