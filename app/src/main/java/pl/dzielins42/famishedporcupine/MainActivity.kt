package pl.dzielins42.famishedporcupine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase
import pl.dzielins42.famishedporcupine.data.source.room.ProductUnit
import timber.log.Timber
import androidx.lifecycle.Observer
import java.util.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        viewModel.viewState.observe(this, Observer {
            Timber.d(it.toString())
        })
    }
}
