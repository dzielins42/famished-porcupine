package pl.dzielins42.famishedporcupine.business

import io.reactivex.Completable
import io.reactivex.Flowable
import org.joda.time.DateTime
import org.joda.time.Months
import org.joda.time.Weeks
import pl.dzielins42.famishedporcupine.data.model.Freshness
import pl.dzielins42.famishedporcupine.data.model.ProductShelfExtended
import pl.dzielins42.famishedporcupine.data.model.ProductUnitExtended
import pl.dzielins42.famishedporcupine.data.repository.PantryRepository
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit

class PantryInteractor(
    private val pantryRepository: PantryRepository
) {
    fun observeProductShelves(): Flowable<List<ProductShelfExtended>> {
        return pantryRepository.observeProductShelves()
            .map { list ->
                list.map { productShelf ->
                    ProductShelfExtended(
                        productShelf,
                        productShelf.products.map { unit ->
                            ProductUnitExtended(unit, calculateProductUnitFreshness(unit))
                        }
                    )
                }
            }
    }

    fun addProductShelf(productShelf: ProductShelf): Completable {
        return pantryRepository.saveProductShelf(productShelf)
    }

    fun addProductUnit(productUnit: ProductUnit): Completable {
        return pantryRepository.saveProductUnit(productUnit)
    }

    private fun calculateProductUnitFreshness(unit: ProductUnit): Freshness {
        val nowDateTime = DateTime.now()
        val unitExpirationDateDateTime = DateTime(unit.expirationDate.time)

        val weeksBetween = Weeks.weeksBetween(
            nowDateTime, unitExpirationDateDateTime
        )
        val monthsBetween = Months.monthsBetween(
            nowDateTime, unitExpirationDateDateTime
        )

        return when {
            weeksBetween.weeks < 1 -> Freshness.RED
            monthsBetween.months < 1 -> Freshness.ORANGE
            else -> Freshness.GREEN
        }
    }
}