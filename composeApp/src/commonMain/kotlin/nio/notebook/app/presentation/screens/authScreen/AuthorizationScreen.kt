package nio.notebook.app.presentation.screens.authScreen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AuthorizationScreen(navController: NavController) {
    BoxWithConstraints {
        println("width$maxWidth")
        println("height$maxHeight")

        when(maxWidth){
            in 0.dp..505.dp -> MobileAuthorizationScreen(navController)
            in 506.dp..1000.dp -> LongAuthorizationScreen(navController)
            else -> ExtendedAuthorizationScreen(navController)
        }
    }
}
