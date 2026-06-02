package psm.lab.examplekoinapp.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel responsible for managing real‑time hardware sensor data and exposing
 * it as observable state to the Jetpack Compose UI.
 *
 * The ViewModel registers listeners for all available sensors (light,
 * accelerometer, proximity and rotation vector) and updates corresponding
 * [StateFlow] objects. Each sensor has a dedicated availability flag to allow
 * the UI to gracefully handle devices lacking specific hardware components.
 *
 * Sensor listeners are registered and unregistered explicitly through
 * [startListening] and [stopListening], allowing lifecycle-aware control from
 * Compose screens.
 *
 * @property sensorManager Android system service used to access hardware sensors.
 */
class SensorsVM( private val sensorManager: SensorManager) : ViewModel() {

    // -------------------------------------------------------------------------
    // Light sensor
    //

    /** Light sensor instance or null if the device does not support it. */
    private val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    /** Indicates whether the device has a light sensor. */
    private val _hasLightSensor = MutableStateFlow(lightSensor != null)
    val hasLightSensor: StateFlow<Boolean> = _hasLightSensor.asStateFlow()

    /** Current light intensity value in lux. */
    private val _light = MutableStateFlow(0.0f)
    val light: StateFlow<Float> = _light.asStateFlow()

    // -------------------------------------------------------------------------
    // Accelerometer
    // -------------------------------------------------------------------------

    /** Accelerometer sensor instance or null if unavailable. */
    private val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    /** Indicates whether the device has an accelerometer. */
    private val _hasAccSensor = MutableStateFlow(accSensor != null)
    val hasAccSensor: StateFlow<Boolean> = _hasAccSensor.asStateFlow()

    /** Current accelerometer reading (x, y, z). */
    private val _acc = MutableStateFlow(Triple(0.0f, 0.0f, 0.0f))
    val acc: StateFlow<Triple<Float, Float, Float>> = _acc.asStateFlow()

    // -------------------------------------------------------------------------
    // Proximity sensor
    // -------------------------------------------------------------------------

    /** Proximity sensor instance or null if unavailable. */
    private val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

    /** Indicates whether the device has a proximity sensor. */
    private val _hasProximitySensor = MutableStateFlow(proximitySensor != null)
    val hasProximitySensor: StateFlow<Boolean> = _hasProximitySensor.asStateFlow()

    /** Current proximity value in centimeters (or binary depending on hardware). */
    private val _proximity = MutableStateFlow(0.0f)
    val proximity: StateFlow<Float> = _proximity.asStateFlow()


    // -------------------------------------------------------------------------
    // Rotation vector / magnetometer (azimuth)
    // -------------------------------------------------------------------------

    /** Rotation vector sensor used to compute device orientation (azimuth). */
    private val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    /** Indicates whether the device supports rotation vector sensor. */
    private val _hasRotationVecorSensor = MutableStateFlow(rotationVectorSensor != null)
    val hasRotationVecorSensor: StateFlow<Boolean> = _hasRotationVecorSensor.asStateFlow()

    /** Current azimuth angle in degrees (0–360°). */
    private val _azimuth= MutableStateFlow(0.0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    // -------------------------------------------------------------------------
    // Sensor event listener
    // -------------------------------------------------------------------------

    /**
     * Shared listener receiving updates from all registered sensors.
     *
     * Converts raw [SensorEvent] data into strongly typed values and updates
     * the corresponding state flows.
     */
    private val listener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) { }
        override fun onSensorChanged(p0: SensorEvent?) {
            if (p0?.sensor?.type == Sensor.TYPE_LIGHT) {
                Log.i("SENSOR", "light = ${p0.values[0]}")
                p0.values?.firstOrNull()?.let { _light.value = it }
            }
            if (p0?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                val values = p0.values
                val x = values.getOrNull(0) ?: 0f
                val y = values.getOrNull(1) ?: 0f
                val z = values.getOrNull(2) ?: 0f
                Log.i("SENSOR", "acc = x=$x, y=$y, z=$z")
                _acc.value = Triple(x, y, z)
            }
            if (p0?.sensor?.type == Sensor.TYPE_PROXIMITY) {
                Log.i("SENSOR", "Proximity = ${p0.values[0]}")
                p0.values?.firstOrNull()?.let { _proximity.value = it }
            }
            if (p0?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
                val rotationMatrix = FloatArray(9)
                SensorManager.getRotationMatrixFromVector(rotationMatrix, p0.values)

                val orientation = FloatArray(3)
                SensorManager.getOrientation(rotationMatrix, orientation)

                val azimuthRad = orientation[0]
                val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
                _azimuth.value = (azimuthDeg + 360) % 360
                Log.i("KOMPAS", "RAD = ${azimuthRad}, DEG = ${azimuthDeg}, A = ${azimuth.value}")

            }
        }
    }

    // -------------------------------------------------------------------------
    // Public API
    // -------------------------------------------------------------------------

    /**
     * Registers listeners for all available sensors.
     *
     * Should be called when the UI becomes active (e.g., in `onResume` of
     * a Compose lifecycle observer).
     */
    fun startListening() {
        lightSensor?.let {
            sensorManager.registerListener(listener,
                it,
                SensorManager.SENSOR_DELAY_UI)
        }
        accSensor?.let {
            sensorManager.registerListener(listener,
                it,
                SensorManager.SENSOR_DELAY_UI)
        }
        proximitySensor?.let {
            sensorManager.registerListener(listener,
                it,
                SensorManager.SENSOR_DELAY_UI)
        }
        rotationVectorSensor?.let {
            sensorManager.registerListener(listener,
                it,
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    /**
     * Unregisters the shared sensor listener.
     *
     * Should be called when the UI becomes inactive (e.g., in `onPause`)
     * to avoid unnecessary battery drain and CPU usage.
     */
    fun stopListening() {
        sensorManager.unregisterListener(listener)
    }
}