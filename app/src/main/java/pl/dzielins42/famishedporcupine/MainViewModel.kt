package pl.dzielins42.famishedporcupine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<List<ProductShelf>>
        get() = mutableViewStateLiveData
    private val mutableViewStateLiveData = MutableLiveData<List<ProductShelf>>()

    init {
        mutableViewStateLiveData.value = createMockData()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun createMockData(): List<ProductShelf> {
        return ArrayList<ProductShelf>().apply {
            for (i in 1..10) {
                add(
                    ProductShelf(
                        definition = ProductDefinition(
                            id = i.toLong(),
                            name = "Product$i"
                        ),
                        products = ArrayList<ProductUnit>().apply {
                            for (j in 1..i) {
                                add(
                                    ProductUnit(
                                        id = (i * 100 + j).toLong(),
                                        definitionId = i.toLong(),
                                        expirationData = Date()
                                    )
                                )
                            }
                        }
                    )
                )
            }
        }
    }
}