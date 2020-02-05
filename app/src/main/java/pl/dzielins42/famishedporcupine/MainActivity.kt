package pl.dzielins42.famishedporcupine

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.flexibleadapter.items.IHolder
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_product_shelf.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.famishedporcupine.data.source.room.ProductShelf
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter = FlexibleAdapter<ProductShelfItem>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()

        val db = Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            "famished-porcupine.db"
        ).build()

        /*
        val subscribtion = db.productDefinitionsDao().insert(
            ProductDefinition(
                0L, "test"
            )
        ).subscribeOn(Schedulers.io())
            .flatMap {
                db.productUnitDao().insert(
                    ProductUnit(
                        0L, it, Date()
                    ),
                    ProductUnit(
                        0L, it, Date()
                    )
                ).subscribeOn(Schedulers.io())
            }.flatMapPublisher {
                db.productShelvesDao().getAll().subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread()).subscribe {
                Timber.d(it.toString())
            }
        */

        viewModel.viewState.observe(this, Observer { viewState ->
            Timber.d(viewState.toString())
            adapter.updateDataSet(
                viewState.map { ProductShelfItem(it) },
                true
            )
        })
    }

    private fun setupUi() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
            addItemDecoration(
                FlexibleItemDecoration(this@MainActivity)
                    .withOffset(8)
                    .withEdge(true)
            )
        }
    }
}
