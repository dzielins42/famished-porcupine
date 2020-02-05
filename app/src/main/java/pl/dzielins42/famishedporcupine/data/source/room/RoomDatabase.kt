package pl.dzielins42.famishedporcupine.data.source.room

import androidx.room.*
import androidx.room.RoomDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

@Database(
    entities = [
        ProductDefinition::class,
        ProductUnit::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun productDefinitionsDao(): ProductDefinitionsDao
    abstract fun productUnitDao(): ProductUnitDao
    abstract fun productShelvesDao(): ProductShelvesDao
}

@Dao
interface ProductShelvesDao {
    @Transaction
    @Query("SELECT * FROM product_definitnions")
    fun getAll(): Flowable<List<ProductShelf>>
}

@Dao
interface ProductDefinitionsDao : BaseCrudDao<ProductDefinition> {
    @Query("SELECT * FROM product_definitnions")
    override fun getAll(): Flowable<List<ProductDefinition>>

    @Query("SELECT * FROM product_definitnions WHERE id == :id")
    override fun get(id: Long): Flowable<ProductDefinition>

    @Query("DELETE FROM product_definitnions WHERE id = :id")
    override fun delete(id: Long): Completable
}

@Dao
interface ProductUnitDao : BaseCrudDao<ProductUnit> {
    @Query("SELECT * FROM product_units")
    override fun getAll(): Flowable<List<ProductUnit>>

    @Query("SELECT * FROM product_units WHERE id == :id")
    override fun get(id: Long): Flowable<ProductUnit>

    @Query("DELETE FROM product_units WHERE id = :id")
    override fun delete(id: Long): Completable
}

interface BaseCrudDao<M> {
    fun getAll(): Flowable<List<M>>

    fun get(id: Long): Flowable<M>

    @Insert
    fun insert(record: M): Single<Long>

    @Insert
    fun insert(vararg records: M): Single<List<Long>>

    @Update
    fun update(record: M): Completable

    @Update
    fun update(vararg records: M): Completable

    @Delete
    fun delete(record: M): Completable

    @Delete
    fun delete(vararg records: M): Completable

    fun delete(id: Long): Completable
}

class Converters {
    @TypeConverter
    fun convertTimestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun convertDateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}
