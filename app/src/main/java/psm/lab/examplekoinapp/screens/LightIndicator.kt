package psm.lab.examplekoinapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
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
 * Visual indicator representing the current ambient light intensity.
 *
 * The component displays a sun icon whose color and size dynamically adapt
 * to the provided [light] value. The raw sensor reading (in lux) is normalized
 * to the 0–1 range assuming a typical maximum of 10,000 lx, producing a smooth
 * transition from a dim, small icon to a bright, enlarged one. The icon tint
 * shifts along the yellow spectrum, becoming lighter as the ambient light
 * increases.
 *
 * Below the icon, the exact light value is displayed in lux for precise
 * sensor readout. This composable is purely visual and does not interact
 * with the sensor system directly; it is intended to be used together with
 * a ViewModel that exposes real-time light sensor data.
 *
 * @param light current ambient light intensity in lux
 */
@SuppressLint("DefaultLocale")
@Composable
fun LightIndicator(light: Float) {

    // Normalization (0–10000 lx → 0–1)
    val normalized = (light / 10000f).coerceIn(0f, 1f)

    // KColor transition from dark to bright yellow
    val color = Color.hsl(
        hue = 50f,                // żółty
        saturation = 1f,
        lightness = 0.3f + normalized * 0.5f
    )

    // Icon size scales with light intensity
    val size = (48 + normalized * 48).dp

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 8.dp)) {
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(size)
        )
        Text(
            text = stringResource(R.string.light_value, light),
            modifier = Modifier.padding(2.dp)
        )
    }
}
