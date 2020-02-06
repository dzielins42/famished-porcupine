package pl.dzielins42.famishedporcupine

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), ProductShelfItem.OnActionClickListener {

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
                viewState.map { ProductShelfItem(it, this) }
            )
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (BuildConfig.DEBUG) {
            menuInflater.inflate(R.menu.dev, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_prepopulate -> {
                viewModel.onCreateMockProductShelf()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onAddActionClick(item: ProductShelfItem) {
        Timber.d("onAddActionClick item=${item.model}")
        viewModel.addProductUnit(
            // TODO This is just a mock
            ProductUnit(
                id = 0L,
                definitionId = item.model.definition.id,
                expirationData = Date()
            )
        )
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
