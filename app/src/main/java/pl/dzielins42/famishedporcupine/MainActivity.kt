package pl.dzielins42.famishedporcupine

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import timber.log.Timber
import java.util.*

class MainActivity : AppCompatActivity(), ProductShelfItem.OnActionClickListener {

    private val viewModel by viewModel<MainViewModel>()
    private val adapter = FlexibleAdapter<ProductShelfItem>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUi()

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
        showDatePicker(MaterialPickerOnPositiveButtonClickListener { selectedDate ->
            viewModel.addProductUnit(
                ProductUnit(
                    id = 0L,
                    definitionId = item.model.definition.id,
                    expirationData = Date(selectedDate)
                )
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
