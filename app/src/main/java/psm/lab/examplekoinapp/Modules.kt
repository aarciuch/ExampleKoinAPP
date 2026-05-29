package psm.lab.examplekoinapp

import android.content.Context
import android.hardware.SensorManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import psm.lab.examplekoinapp.sensors.SensorsVM

val sensorModule = module {

    single {
        androidContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    viewModel { SensorsVM(get()) }
}
