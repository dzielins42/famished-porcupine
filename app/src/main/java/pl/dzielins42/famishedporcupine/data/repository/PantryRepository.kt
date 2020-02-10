package pl.dzielins42.famishedporcupine.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit

interface PantryRepository {
    fun observeProductShelves(): Flowable<List<ProductShelf>>
    fun saveProductShelf(productShelf: ProductShelf): Completable
    fun saveProductUnit(productUnit: ProductUnit): Completable
    fun deleteProductUnit(productUnitId: Long): Completable
}