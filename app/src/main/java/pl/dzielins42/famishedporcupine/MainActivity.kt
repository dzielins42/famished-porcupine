package pl.dzielins42.famishedporcupine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase
import pl.dzielins42.famishedporcupine.data.source.room.ShelfProduct
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            "famished-porcupine.db"
        ).build()

        val subscribtion = db.productDefinitionsDao().insert(
            ProductDefinition(
                0L, "test"
            )
        ).subscribeOn(Schedulers.io())
            .flatMap {
                db.shelfProductsDao().insert(
                    ShelfProduct(
                        0L, it, Date()
                    ),
                    ShelfProduct(
                        0L, it, Date()
                    )
                ).subscribeOn(Schedulers.io())
            }.flatMapPublisher {
                db.shelfSectionsDao().getAll().subscribeOn(Schedulers.io())
            }.observeOn(AndroidSchedulers.mainThread()).subscribe {
                Log.d("test", it.toString())
            }
    }
}
