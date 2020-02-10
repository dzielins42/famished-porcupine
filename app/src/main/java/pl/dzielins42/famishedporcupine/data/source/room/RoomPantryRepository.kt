package pl.dzielins42.famishedporcupine.data.source.room

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import pl.dzielins42.famishedporcupine.data.repository.PantryRepository

class RoomPantryRepository(
    private val roomDatabase: RoomDatabase
) : PantryRepository {

    override fun observeProductShelves(): Flowable<List<ProductShelf>> {
        return roomDatabase.productShelvesDao().getAll()
            .subscribeOn(Schedulers.io())
    }

    override fun saveProductShelf(productShelf: ProductShelf): Completable {
        return Completable.fromAction {
            roomDatabase.runInTransaction {
                roomDatabase.productDefinitionsDao().insert(productShelf.definition)
                    .flatMap { definitionId ->
                        roomDatabase.productUnitDao().insert(
                            *productShelf.products
                                .map { it.copy(definitionId = definitionId) }
                                .toTypedArray()
                        )
                    }.ignoreElement().blockingGet()
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun saveProductUnit(productUnit: ProductUnit): Completable {
        val productUnitDao = roomDatabase.productUnitDao()
        val isUpdate = productUnit.id != 0L
        val action = if (isUpdate) {
            productUnitDao.update(productUnit)
        } else {
            productUnitDao.insert(productUnit).ignoreElement()
        }

        return action.subscribeOn(Schedulers.io())
    }

    override fun deleteProductUnit(productUnitId: Long): Completable {
        return roomDatabase.productUnitDao().delete(productUnitId)
            .subscribeOn(Schedulers.io())
    }
}