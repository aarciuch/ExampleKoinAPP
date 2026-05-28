package psm.lab.examplekoinapp.screens

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import psm.lab.examplekoinapp.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Home


enum class Screens(
    val route: String,
    @StringRes val labelRes: Int,
    val icon: ImageVector
) {
    HOME(
        "home",
         labelRes = R.string.home,
         icon = Icons.Default.Home
    ),
    PAGE1(
        "page_1",
        labelRes = R.string.page_1,
        icon = Icons.Default.Android
    ),
    PAGE2(
        "page_2",
        labelRes = R.string.page_2,
        icon = Icons.Default.Adb
    ),


}

