package pl.dzielins42.famishedporcupine

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import pl.dzielins42.famishedporcupine.business.PantryInteractor
import pl.dzielins42.famishedporcupine.data.repository.PantryRepository
import pl.dzielins42.famishedporcupine.data.source.room.RoomDatabase
import pl.dzielins42.famishedporcupine.data.source.room.RoomPantryRepository
import timber.log.Timber

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@MyApplication)
            modules(listOf(
                // ViewModels
                module { viewModel { MainViewModel(get()) } },
                // Business logic
                module { single { PantryInteractor(get()) } },
                // Data
                module {
                    fun provideRoomDatabase(application: Application): RoomDatabase {
                        return Room.databaseBuilder(
                            application,
                            RoomDatabase::class.java,
                            DB_NAME
                        ).build()
                    }

                    single { provideRoomDatabase(androidApplication()) }
                    single<PantryRepository> { RoomPantryRepository(get()) }
                }
            ))
        }
    }

    companion object {
        private const val DB_NAME = "famished-porcupine.db"
    }
}