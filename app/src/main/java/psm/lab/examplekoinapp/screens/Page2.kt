package psm.lab.examplekoinapp.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp

/**
 * Secondary placeholder screen used as one of the destinations
 * in the application's navigation graph.
 *
 * This composable currently displays a simple text label and does not
 * contain any business logic. It serves as a template for future content
 * and demonstrates navigation structure within the app.
 */
@Composable
fun Page2() {
    Column() {
        Text(modifier = Modifier.padding(24.dp), text = "Page 2")
        CameraScreen()
    }
}


@Composable
fun CameraScreen() {

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { photo: Bitmap? ->

            bitmap = photo
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                cameraLauncher.launch()
            }
        ) {
            Text("Zrób zdjęcie")
        }

        bitmap?.let { image ->

            Image(
                bitmap = image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
        }
    }
}