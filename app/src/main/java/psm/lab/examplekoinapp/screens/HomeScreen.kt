package psm.lab.examplekoinapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Default landing screen of the application.
 *
 * This composable displays a simple placeholder text and serves as the
 * starting destination in the navigation graph. It does not contain any
 * business logic and is intended to be replaced or extended with actual
 * home screen content in future iterations of the app.
 */
@Composable
fun HomeScreen() {
    Column {
        Row {
            Text(modifier = Modifier.padding(24.dp), text = "Home screen")
        }
        Row {
            Text(modifier = Modifier.padding(24.dp), text = "Ble ble")
        }
        Row {
            Text(modifier = Modifier.padding(24.dp), text = "Ble1 ble1")
        }
    }
}