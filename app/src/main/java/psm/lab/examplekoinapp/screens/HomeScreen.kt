package psm.lab.examplekoinapp.screens

import android.R.attr.text
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Text(modifier = Modifier.padding(24.dp), text = "Home screen")
}