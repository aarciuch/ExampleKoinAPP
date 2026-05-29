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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@SuppressLint("DefaultLocale")
@Composable
fun LightIndicator(light: Float) {

    // Normalizacja wartości (0–10000 lx → 0–1)
    val normalized = (light / 10000f).coerceIn(0f, 1f)

    // Kolor od ciemnego → jasny
    val color = Color.hsl(
        hue = 50f,                // żółty
        saturation = 1f,
        lightness = 0.3f + normalized * 0.5f
    )

    // Rozmiar ikonki zależny od światła
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
            text = String.format("Light: %.2f lx", light),
            modifier = Modifier.padding(start = 2.dp)
        )
    }
}
