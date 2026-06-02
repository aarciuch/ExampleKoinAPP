package psm.lab.examplekoinapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


/**
 * Application class responsible for initializing global dependency injection
 * using the Koin framework.
 *
 * This class is instantiated before any Activity or Composable is created,
 * ensuring that all modules (ViewModels, repositories, data sources, sensor
 * services, Room database, Retrofit clients, etc.) are available throughout
 * the entire application lifecycle.
 *
 * The Koin container is started once at application startup and provides
 * a centralized dependency graph for the MVVM architecture.
 */


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                sensorModule,
                // add another modules here
                // viewModelModule,
                // repositoryModule,
                // networkModule,
                // databaseModule,
                // etc
            )
        }
    }
}