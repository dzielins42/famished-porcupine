package pl.dzielins42.famishedporcupine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.dzielins42.famishedporcupine.data.source.room.ProductDefinition
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(
            applicationContext,
            RoomDatabase::class.java,
            "famished-porcupine.db"
        ).build()

        db.productDefinitionsDao().insert(
            ProductDefinition(
                123L, "test"
            )
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        val test = db.productDefinitionsDao().getAll().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            Log.d("test", it.toString())
        }
    }
}
