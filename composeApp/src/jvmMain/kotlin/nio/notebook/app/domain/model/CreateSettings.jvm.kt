package nio.notebook.app.domain.model

import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import java.util.prefs.Preferences

actual fun createSettings(): Settings =
    PreferencesSettings(
        Preferences.userRoot().node("nio/notebook")
    )