package psm.lab.examplekoinapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import org.koin.compose.viewmodel.koinViewModel
import psm.lab.examplekoinapp.sensors.SensorsVM

@Composable
fun Page1(vm: SensorsVM = koinViewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val light by vm.light.collectAsState()
    val hasLightSensor by vm.hasLightSensor.collectAsState()

    val acc by vm.acc.collectAsState()
    val hasAccSensor by vm.hasAccSensor.collectAsState()

    val proximity by vm.proximity.collectAsState()
    val hasProximitySensor by vm.hasProximitySensor.collectAsState()

    val azimuth by vm.azimuth.collectAsState()
    val hasRotationVector by vm.hasRotationVecorSensor.collectAsState()


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event->
            when(event) {
                Lifecycle.Event.ON_RESUME -> vm.startListening()
                Lifecycle.Event.ON_PAUSE -> vm.stopListening()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    SensorsGrid(light, acc, proximity, azimuth,
                hasLightSensor, hasAccSensor, hasProximitySensor, hasRotationVector)
}


