package psm.lab.examplekoinapp

import android.content.Context
import android.hardware.SensorManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import psm.lab.examplekoinapp.sensors.SensorsVM


/**
 * Koin module providing dependencies required for accessing Android hardware sensors.
 *
 * Registers a singleton instance of [SensorManager] and exposes it to the
 * dependency graph. The module also declares the [SensorsVM] ViewModel,
 * which consumes the sensor manager to observe and process sensor events.
 *
 * This module forms the foundation of the sensor subsystem and should be
 * loaded at application startup.
 */

val sensorModule = module {

    single {
        androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    viewModel { SensorsVM(get()) }
}
