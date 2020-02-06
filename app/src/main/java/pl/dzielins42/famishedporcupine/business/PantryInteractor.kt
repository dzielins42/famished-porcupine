package pl.dzielins42.famishedporcupine.business

import io.reactivex.Completable
import io.reactivex.Flowable
import pl.dzielins42.famishedporcupine.data.repository.PantryRepository
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit

class PantryInteractor(
    private val pantryRepository: PantryRepository
) {
    fun observeProductShelves(): Flowable<List<ProductShelf>> {
        return pantryRepository.observeProductShelves()
    }

    fun addProductShelf(productShelf: ProductShelf): Completable {
        return pantryRepository.saveProductShelf(productShelf)
    }

    fun addProductUnit(productUnit: ProductUnit): Completable {
        return pantryRepository.saveProductUnit(productUnit)
    }
}