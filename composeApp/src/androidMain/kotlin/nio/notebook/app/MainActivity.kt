package nio.notebook.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.init
import nio.notebook.app.core.errorHandler.db.platformModule
import nio.notebook.app.core.errorHandler.di.errorModule
import nio.notebook.app.di.settingsModule
import nio.notebook.app.di.viewModelModule
import nio.notebook.app.presentation.navigation.AppNavigation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(platformModule)
            modules(settingsModule)
            modules(errorModule)
            modules(viewModelModule)
        }
        FileKit.init(this)
        setContent {
            AppNavigation()
        }
    }
}
