package psm.lab.examplekoinapp.screens

import android.R.attr.top
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntryAsState().
            value?.destination?.route
    val colors = listOf(Color.Red, Color.Green, Color.Blue)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Screens.values().forEach { screen ->
            val selected = currentRoute == screen.route

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        else Color.Transparent
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .clickable {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                    }
                }
            ) {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = stringResource(screen.labelRes),
                   // tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                )
                Text(
                    text = stringResource(screen.labelRes),
                    //color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }

}