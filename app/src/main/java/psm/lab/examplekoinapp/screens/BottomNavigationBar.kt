package psm.lab.examplekoinapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom navigation bar displayed across the main screens of the application.
 *
 * The bar renders a set of navigation items defined in [Screens], each consisting
 * of an icon and a text label. The currently active destination is highlighted
 * using a subtle background indicator. Tapping an item triggers navigation to
 * the corresponding route using the provided [NavController].
 *
 * This composable is intended to be used inside a [Scaffold] as the `bottomBar`
 * component and supports single-top navigation to avoid duplicate destinations
 * in the back stack.
 *
 * @param navController controller used to manage navigation between screens
 */
@Composable
fun BottomNavigationBar(navController: NavController) {

    val currentRoute = navController.currentBackStackEntryAsState().
            value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 48.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Screens.entries.forEach { screen ->
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