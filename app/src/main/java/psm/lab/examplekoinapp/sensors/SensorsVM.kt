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

class SensorsVM( private val sensorManager: SensorManager) : ViewModel() {
    // **************** Light
    private val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    private val _hasLightSensor = MutableStateFlow(lightSensor != null)
    val hasLightSensor: StateFlow<Boolean> = _hasLightSensor.asStateFlow()
    private val _light = MutableStateFlow(0.0f)
    val light: StateFlow<Float> = _light.asStateFlow()

    // ***************** Acc
    private val accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val _hasAccSensor = MutableStateFlow(accSensor != null)
    val hasAccSensor: StateFlow<Boolean> = _hasAccSensor.asStateFlow()
    private val _acc = MutableStateFlow(Triple(0.0f, 0.0f, 0.0f))
    val acc: StateFlow<Triple<Float, Float, Float>> = _acc.asStateFlow()

    // ******************* Proximity
    private val proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
    private val _hasProximitySensor = MutableStateFlow(proximitySensor != null)
    val hasProximitySensor: StateFlow<Boolean> = _hasProximitySensor.asStateFlow()
    private val _proximity = MutableStateFlow(0.0f)
    val proximity: StateFlow<Float> = _proximity.asStateFlow()


    // ******************** Magnetometer
    private val rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
    private val _hasRotationVecorSensor = MutableStateFlow(rotationVectorSensor != null)
    val hasRotationVecorSensor: StateFlow<Boolean> = _hasRotationVecorSensor.asStateFlow()
    private val _azimuth= MutableStateFlow(0.0f)
    val azimuth: StateFlow<Float> = _azimuth.asStateFlow()

    // -----------------------------------------------------------------------------------
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

    fun stopListening() {
        sensorManager.unregisterListener(listener)
    }
}