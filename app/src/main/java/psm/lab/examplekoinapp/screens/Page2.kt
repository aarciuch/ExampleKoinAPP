package psm.lab.examplekoinapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Text(modifier = Modifier.padding(24.dp), text = "Page 2")
}