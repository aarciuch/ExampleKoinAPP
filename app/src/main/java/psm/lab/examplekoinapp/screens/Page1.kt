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

/**
 * Screen responsible for displaying real‑time sensor data using [SensorsVM].
 *
 * This composable collects all sensor-related state flows from the ViewModel
 * and forwards them to [SensorsGrid] for visual presentation. It also manages
 * lifecycle-aware registration of sensor listeners, ensuring that sensors are
 * active only when the screen is visible.
 *
 * @param vm the ViewModel providing sensor data; resolved via Koin by default
 */
@Composable
fun Page1(vm: SensorsVM = koinViewModel()) {

    /** Lifecycle owner used to observe ON_RESUME / ON_PAUSE events. */
    val lifecycleOwner = LocalLifecycleOwner.current

    /** Current light sensor reading in lux. */
    val light by vm.light.collectAsState()

    /** Indicates whether the device has a light sensor. */
    val hasLightSensor by vm.hasLightSensor.collectAsState()

    /** Current accelerometer reading (x, y, z). */
    val acc by vm.acc.collectAsState()

    /** Indicates whether the device has an accelerometer. */
    val hasAccSensor by vm.hasAccSensor.collectAsState()

    /** Current proximity sensor reading. */
    val proximity by vm.proximity.collectAsState()

    /** Indicates whether the device has a proximity sensor. */
    val hasProximitySensor by vm.hasProximitySensor.collectAsState()

    /** Current azimuth (0–360°) computed from the rotation vector sensor. */
    val azimuth by vm.azimuth.collectAsState()

    /** Indicates whether the device supports the rotation vector sensor. */
    val hasRotationVector by vm.hasRotationVecorSensor.collectAsState()

    /**
     * Lifecycle-aware effect that starts and stops sensor listeners.
     *
     * Sensors begin listening when the screen becomes active (`ON_RESUME`)
     * and stop when the screen goes into the background (`ON_PAUSE`).
     * This prevents unnecessary CPU and battery usage.
     */
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

    /**
     * Main UI grid displaying all sensor indicators.
     * Receives both sensor values and availability flags.
     */
    SensorsGrid(light, acc, proximity, azimuth,
                hasLightSensor, hasAccSensor, hasProximitySensor, hasRotationVector)
}


