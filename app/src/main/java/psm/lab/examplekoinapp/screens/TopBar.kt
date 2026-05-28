package psm.lab.examplekoinapp.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar() {
    Text(modifier = Modifier.padding(top = 72.dp, start = 24.dp), text="Top bar")
}