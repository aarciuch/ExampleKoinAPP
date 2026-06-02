package psm.lab.examplekoinapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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

/**
 * Main entry point of the application UI, hosting the Jetpack Compose content
 * and the navigation graph.
 *
 * This activity sets up the application theme, initializes the NavController,
 * and provides the top-level Scaffold structure including the top app bar and
 * bottom navigation bar. It also defines the NavHost responsible for routing
 * between the main screens of the application.
 *
 * The activity uses edge-to-edge mode and relies entirely on Compose for
 * rendering the UI, without any XML layouts.
 */

class MainActivity : ComponentActivity() {

    /**
     * Initializes the activity, configures edge-to-edge mode and composes
     * the root UI structure including theme, navigation and scaffold layout.
     *
     * @param savedInstanceState previously saved state of the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleKoinAPPTheme {

                /**
                 * Navigation controller used to manage screen transitions
                 * within the application.
                 */
                val navController = rememberNavController()

                /**
                 * Top-level layout container providing the main structure of the screen.
                 *
                 * The Scaffold defines the overall UI arrangement, including:
                 * - the top app bar displayed on all primary screens,
                 * - the bottom navigation bar enabling screen switching,
                 * - the content area hosting the navigation graph.
                 *
                 * Padding from the Scaffold is forwarded to the NavHost to ensure
                 * proper layout behavior in edge-to-edge mode.
                 */
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar()
                             },
                    bottomBar = {
                        BottomNavigationBar(navController)
                    }
                ) { innerPadding ->

                    /**
                     * Navigation host defining the available screens and
                     * their corresponding routes.
                     *
                     * @param innerPadding padding provided by the Scaffold
                     */
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


