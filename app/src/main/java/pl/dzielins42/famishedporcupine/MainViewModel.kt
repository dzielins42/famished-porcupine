package pl.dzielins42.famishedporcupine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import pl.dzielins42.famishedporcupine.business.PantryInteractor
import pl.dzielins42.famishedporcupine.data.model.ProductShelfExtended
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import java.util.*

class MainViewModel(
    private val pantryInteractor: PantryInteractor
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<List<ProductShelfExtended>>
        get() = mutableViewStateLiveData
    private val mutableViewStateLiveData = MutableLiveData<List<ProductShelfExtended>>(emptyList())

    init {
        compositeDisposable.add(
            pantryInteractor.observeProductShelves()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { mutableViewStateLiveData.value = it }
        )
    }

    fun addProductUnit(productUnit: ProductUnit) {
        compositeDisposable.add(
            pantryInteractor.addProductUnit(productUnit)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    fun onCreateMockProductShelf() {
        compositeDisposable.add(Single.fromCallable {
            ProductShelf(
                definition = ProductDefinition(
                    id = 0L,
                    name = UUID.randomUUID().toString()
                ),
                products = emptyList()
            )
        }.flatMapCompletable {
            pantryInteractor.addProductShelf(it)
        }.observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}