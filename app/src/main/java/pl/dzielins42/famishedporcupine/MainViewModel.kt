package pl.dzielins42.famishedporcupine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import java.util.*

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val viewState: LiveData<List<ProductShelf>>
        get() = mutableViewStateLiveData
    private val mutableViewStateLiveData = MutableLiveData<List<ProductShelf>>(emptyList())

    fun onCreateMockProductShelf() {
        compositeDisposable.add(Single.fromCallable {
            ProductShelf(
                definition = ProductDefinition(
                    id = 0L,
                    name = UUID.randomUUID().toString()
                ),
                products = emptyList()
            )
        }.subscribe { mockProduct ->
            mutableViewStateLiveData.value = mutableViewStateLiveData.value?.run {
                plus(mockProduct)
            } ?: listOf(mockProduct)
        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}