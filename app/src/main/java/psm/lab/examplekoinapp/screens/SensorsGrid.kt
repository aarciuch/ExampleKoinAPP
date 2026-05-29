package psm.lab.examplekoinapp.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import psm.lab.examplekoinapp.R

@Composable
fun SensorsGrid(
    light: Float,
    acc: Triple<Float, Float, Float>,
    proximity: Float,
    hasLight: Boolean,
    hasAcc: Boolean,
    hasProximity: Boolean
) {
    val (x, y, z) = acc

    Row(modifier = Modifier.fillMaxWidth()) {

        // LEWA KOLUMNA — Light
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        ) {
            if (hasLight) {
                SensorCard {
                    LightIndicator(light)
                }
            } else {
                SensorCard { Text(stringResource(R.string.sensor_light_unavailable)) }
            }
            if (hasProximity) {
                SensorCard {
                    ProximityIndicator(proximity)
                }
            } else {
                SensorCard { Text(stringResource(R.string.sensor_proximity_unavailable)) }
            }
        }

        // PRAWA KOLUMNA — X, Y, Z
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        ) {
            if (hasAcc) {
                SensorCard {
                    AccHotMap(x)
                }
                SensorCard {
                    AccHotMap(y)
                }
                SensorCard {
                    AccHotMap(z)
                }
            } else {
                SensorCard { Text(stringResource(R.string.sensor_acc_unavailable)) }
            }
        }
    }


    // 🔥 tu dodasz kolejne sensory
    // Row {
    //     SensorCard { ProximityIndicator(proximity) }
    //     SensorCard { CompassIndicator(azimuth) }
    //
}

@Composable
fun SensorCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp)
    ) {
        content()
    }
}

fun hotColor(z: Float): Color {
    val norm = ((z + 10f) / 20f).coerceIn(0f, 1f)
    return Color.hsl(
        hue = (240f - 240f * norm),
        saturation = 1f,
        lightness = 0.5f
    )
}

fun coldColor(x: Float): Color {
    val norm = ((x + 10f) / 20f).coerceIn(0f, 1f)
    return Color.hsl(
        hue = (0f + 240f * norm), // red → blue
        saturation = 1f,
        lightness = 0.5f
    )
}

fun neutralColor(y: Float): Color {
    val norm = ((y + 10f) / 20f).coerceIn(0f, 1f)
    return Color.hsl(
        hue = (120f - 60f * norm), // green → yellow
        saturation = 1f,
        lightness = 0.5f
    )
}


@Composable
fun AccHotMap(z: Float) {
    val color = hotColor(z)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Z = %.2f".format(z),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun AccColdMap(x: Float) {
    val color = coldColor(x)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("X = %.2f".format(x), color = Color.White)
    }
}

@Composable
fun AccNeutralMap(y: Float) {
    val color = neutralColor(y)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("Y = %.2f".format(y), color = Color.White)
    }
}


