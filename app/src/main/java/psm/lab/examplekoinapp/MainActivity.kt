package psm.lab.examplekoinapp

import android.R.attr.text
import android.R.attr.top
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import psm.lab.examplekoinapp.screens.BottomNavigationBar
import psm.lab.examplekoinapp.screens.HomeScreen
import psm.lab.examplekoinapp.screens.Page1
import psm.lab.examplekoinapp.screens.Page2
import psm.lab.examplekoinapp.screens.Screens
import psm.lab.examplekoinapp.screens.TopBar
import psm.lab.examplekoinapp.ui.theme.ExampleKoinAPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleKoinAPPTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                             },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.HOME.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screens.HOME.route) { HomeScreen() }
                        composable(Screens.PAGE1.route) { Page1() }
                        composable(Screens.PAGE2.route) { Page2() }

                    }
                }
            }
        }
    }
}


