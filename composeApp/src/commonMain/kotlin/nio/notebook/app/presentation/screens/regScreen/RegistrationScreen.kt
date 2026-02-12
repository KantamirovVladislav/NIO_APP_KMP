package nio.notebook.app.presentation.screens.regScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nio.notebook.app.presentation.screens.authScreen.ExtendedAuthorizationScreen
import nio.notebook.app.presentation.screens.authScreen.LongAuthorizationScreen
import nio.notebook.app.presentation.screens.authScreen.MobileAuthorizationScreen

@Composable
fun RegistrationScreen(navController: NavController) {
    BoxWithConstraints {
        println("width$maxWidth")
        println("height$maxHeight")

        when(maxWidth){
            in 0.dp..505.dp -> MobileRegistrationScreen(navController)
            in 506.dp..1040.dp -> LongRegistrationScreen(navController)
            else -> ExtendedRegistrationScreen(navController)
        }
    }
}
