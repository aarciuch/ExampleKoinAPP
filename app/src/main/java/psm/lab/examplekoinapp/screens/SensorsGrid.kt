package psm.lab.examplekoinapp.screens


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import psm.lab.examplekoinapp.R
import java.lang.Math.cos
import java.lang.Math.sin

@Composable
fun SensorsGrid(
    light: Float,
    acc: Triple<Float, Float, Float>,
    proximity: Float,
    azimuth: Float,
    hasLight: Boolean,
    hasAcc: Boolean,
    hasProximity: Boolean,
    hasRotationVector: Boolean
) {
    val (x, y, z) = acc

    Row(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

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
            if (hasRotationVector) {
                SensorCard{
                    Compass(azimuth)
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.azimuth_value).format(azimuth)
                )
            } else {
                SensorCard { Text(stringResource(R.string.sensor_rotation_vector_unavailable)) }
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
                    AccHotMap(x, "X")
                }
                SensorCard {
                    AccHotMap(y, "Y")
                }
                SensorCard {
                    AccHotMap(z, "Z")
                }
                SensorCard {
                    AccBall(x,y,z)
                }
            } else {
                SensorCard { Text(stringResource(R.string.sensor_acc_unavailable)) }
            }
        }
    }


    // 🔥 tu dodasz kolejne sensory
    // Row { }

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
fun AccHotMap(value: Float, dimension: String = "Z") {
    val color = hotColor(value)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${dimension} = %.2f".format(value),
            color = Color.White,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun AccColdMap(value: Float, dimension: String  ="X") {
    val color = coldColor(value)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("${dimension} = %.2f".format(value), color = Color.White)
    }
}

@Composable
fun AccNeutralMap(value: Float, dimension: String = "Y") {
    val color = neutralColor(value)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(color, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("${dimension} = %.2f".format(value), color = Color.White)
    }
}

@Composable
fun AccBall(
    x: Float,
    y: Float,
    z: Float,
    size: Dp = 140.dp
) {
    val ballSize = 20.dp

    // Normalizacja X/Y do zakresu -1..1
    val nx = (-x / 10f).coerceIn(-1f, 1f)
    val ny = (y / 10f).coerceIn(-1f, 1f)

    // Kolor od niebieskiego do czerwonego
    val color = Color.hsl(
        hue = (240f - 240f * ((z + 10f) / 20f).coerceIn(0f, 1f)),
        saturation = 1f,
        lightness = 0.5f
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .size(ballSize)
                .offset {
                    val half = size.toPx() / 2f
                    val halfBall = ballSize.toPx() / 2f
                    val maxOffset = half - halfBall
                    IntOffset(
                        (half + nx * maxOffset - halfBall).toInt(),
                        (half + ny * maxOffset - halfBall).toInt()
                    )
                }
                .background(color, CircleShape)
        )
    }
}

@Composable
fun Compass(
    azimuth: Float,
    size: Dp = 140.dp
) {
    var lastAzimuth by remember { mutableStateOf(azimuth) }
    val smooth = smoothAzimuth(lastAzimuth, azimuth)
    lastAzimuth = smooth


    val animatedAzimuth by animateFloatAsState(
        targetValue = lastAzimuth,
        animationSpec = tween(durationMillis = 150, easing = LinearEasing)
    )

    Box(
        modifier = Modifier
            .size(size)
            .background(Color(0xFF222222), CircleShape),
        contentAlignment = Alignment.Center
    ) {

        // Tarcza kompasu
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.toPx() / 2f

            // Podziałka co 30°
            for (i in 0 until 360 step 10) {
                val angleRad = Math.toRadians(i.toDouble() - 90)
                val start = Offset(
                    x = radius + cos(angleRad).toFloat() * (radius - 10.dp.toPx()),
                    y = radius + sin(angleRad).toFloat() * (radius - 10.dp.toPx())
                )
                val end = Offset(
                    x = radius + cos(angleRad).toFloat() * radius,
                    y = radius + sin(angleRad).toFloat() * radius
                )
                drawLine(Color.White, start, end, strokeWidth = 3f)
            }
        }

        // Igła kompasu
        Box(
            modifier = Modifier
                .fillMaxSize()
                .rotate(animatedAzimuth)   // OBRÓT IGŁY
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.toPx() / 2f

                // Igła — czerwona (N)
                drawLine(
                    color = Color.Red,
                    start = Offset(radius, radius),
                    end = Offset(radius, radius - radius + 20.dp.toPx()),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )

                // Igła — biała (S)
                drawLine(
                    color = Color.White,
                    start = Offset(radius, radius),
                    end = Offset(radius, radius + radius - 20.dp.toPx()),
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}
fun smoothAzimuth(prev: Float, new: Float): Float {
    var diff = new - prev

    if (diff > 180f) diff -= 360f
    if (diff < -180f) diff += 360f

    return prev + diff
}





