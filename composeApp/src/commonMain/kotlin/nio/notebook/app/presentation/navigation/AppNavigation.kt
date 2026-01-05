package nio.notebook.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import nio.notebook.app.core.errorHandler.ui.ErrorViewPosition
import nio.notebook.app.core.errorHandler.ui.ViewErrorHandler
import nio.notebook.app.data.storage.SettingsDataStore
import nio.notebook.app.presentation.screens.directorySelector.DirectorySelectorScreen
import nio.notebook.app.presentation.screens.onBoarding.OnBoardingScreen
import org.koin.compose.koinInject

@Serializable
sealed class AppRouter(val route: String) {
    @Serializable
    object Onboarding : AppRouter("onboarding")

    @Serializable
    object DirectorySelector : AppRouter("directory_selector")
}


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val settingsDataStore = koinInject<SettingsDataStore>()

    NavHost(
        navController = navController,
        startDestination = if (!settingsDataStore.getEntryOnBoarding()) AppRouter.Onboarding.route else AppRouter.DirectorySelector.route
    ) {
        composable(AppRouter.Onboarding.route) {
            OnBoardingScreen(navController = navController)
            ViewErrorHandler(position = ErrorViewPosition.TopRight)
        }

        composable(AppRouter.DirectorySelector.route) {
            DirectorySelectorScreen()
        }
    }
}