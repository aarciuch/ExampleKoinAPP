package psm.lab.examplekoinapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import psm.lab.examplekoinapp.R

/**
 * Visual indicator representing the current proximity sensor reading.
 *
 * The component displays a radar-style icon whose size and color dynamically
 * adapt to the provided [proximity] value. The raw sensor reading (typically
 * in centimeters) is normalized to the 0–1 range assuming a maximum distance
 * of 10 cm, producing a smooth transition from a dim, small icon to a bright,
 * enlarged one. The icon tint shifts along the yellow spectrum, becoming
 * lighter as the detected distance increases.
 *
 * Below the icon, the exact proximity value is displayed in centimeters.
 * This composable is purely visual and does not interact with the sensor
 * system directly; it is intended to be used together with a ViewModel that
 * exposes real-time proximity sensor data.
 *
 * @param proximity current proximity sensor reading in centimeters
 */
@SuppressLint("DefaultLocale")
@Composable
fun ProximityIndicator(proximity: Float) {

    // Normalization (0–10 cm → 0–1)
    val normalized = (proximity / 10f).coerceIn(0f, 1f)

    // Color transition from dark to bright yellow
    //    val color = Color.hsl(
    val color = Color.hsl(
        hue = 50f,                // yellow
        saturation = 1f,
        lightness = 0.3f + normalized * 0.5f
    )

    // Icon size scales with proximity distance
    val size = (48 + normalized * 48).dp

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 8.dp)) {
        Icon(
            imageVector = Icons.Default.Radar,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(size)
        )

        Text(
            text = stringResource(R.string.proximity_value, proximity),
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}
