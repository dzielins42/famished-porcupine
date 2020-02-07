package pl.dzielins42.famishedporcupine

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import pl.dzielins42.famishedporcupine.item.ProductShelfItem
import pl.dzielins42.famishedporcupine.item.ProductUnitItem
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), ProductShelfItem.OnActionClickListener {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter = FlexibleAdapter<IFlexible<*>>(emptyList(), null, true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()

        viewModel.viewState.observe(this, Observer { viewState ->
            Timber.d(viewState.toString())
            adapter.updateDataSet(
                viewState.map { productShelf ->
                    ProductShelfItem(
                        productShelf,
                        this
                    ).apply {
                        isExpanded = false
                        productShelf.units.map { unit ->
                            ProductUnitItem(unit, this)
                        }.forEach { unit -> this.addSubItem(unit) }
                    }
                }
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
        showDatePicker(MaterialPickerOnPositiveButtonClickListener { selectedDate ->
            viewModel.addProductUnit(
                ProductUnit(
                    id = 0L,
                    definitionId = item.model.definition.id,
                    expirationDate = Date(selectedDate)
                )
            )
        })
    }

    private fun setupUi() {
        adapter.addListener(object : FlexibleAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int): Boolean {
                Timber.d("click")
                return true
            }
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
        adapter.setDisplayHeadersAtStartUp(true)
            .setStickyHeaders(true)
    }

    private fun showDatePicker(
        onPositiveButtonClickListener: MaterialPickerOnPositiveButtonClickListener<Long>
    ) {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .setTitleText(R.string.title_select_expiration_date)
            .build()

        picker.addOnPositiveButtonClickListener(onPositiveButtonClickListener)

        picker.show(supportFragmentManager, picker.hashCode().toString())
    }
}
