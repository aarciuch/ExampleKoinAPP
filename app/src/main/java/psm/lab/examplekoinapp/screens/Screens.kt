package psm.lab.examplekoinapp.screens

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import psm.lab.examplekoinapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Home

/**
 * Enumeration defining all navigation destinations used in the application.
 *
 * Each entry represents a screen in the navigation graph and provides:
 * - a unique [route] string used by the NavController,
 * - a string resource ID ([labelRes]) for UI labels such as bottom navigation,
 * - an [icon] representing the screen in navigation components.
 *
 * This enum centralizes navigation metadata, ensuring type‑safety and
 * preventing inconsistencies between routes, labels, and icons across the app.
 */
enum class Screens(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    /** Home screen displayed as the main entry point of the application. */
    HOME(
        "home",
         labelRes = R.string.home,
         icon = Icons.Default.Home
    ),

    /** Screen presenting real‑time sensor data collected from the device. */
    PAGE1(
        "page_1",
        labelRes = R.string.page_1,
        icon = Icons.Default.Android
    ),

    /** Secondary placeholder screen used for demonstration or future expansion. */
    PAGE2(
        "page_2",
        labelRes = R.string.page_2,
        icon = Icons.Default.Adb
    ),


}

