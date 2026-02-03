package nio.notebook.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nio.notebook.app.core.errorHandler.db.platformModule
import nio.notebook.app.core.errorHandler.di.errorModule
import nio.notebook.app.di.viewModelModule
import nio.notebook.app.presentation.navigation.AppNavigation
import org.koin.core.context.startKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import nio.notebook.app.di.settingsModule
import nio_app.composeapp.generated.resources.Res
import nio_app.composeapp.generated.resources.app_logo
import org.jetbrains.compose.resources.painterResource
import java.util.logging.Level
import java.util.logging.Logger


fun main() = application {
    initLogging()
    startKoin {
        modules(platformModule)
        modules(settingsModule)
        modules(errorModule)
        modules(viewModelModule)

    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "NIO_APP",
        icon = painterResource(Res.drawable.app_logo),
    ) {
        AppNavigation()
    }
}

private fun initLogging() {

    Napier.base(DebugAntilog())
}

