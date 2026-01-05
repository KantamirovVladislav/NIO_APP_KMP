package nio.notebook.app.data.storage

import androidx.compose.ui.unit.Constraints
import com.russhwolf.settings.Settings
import nio.notebook.app.core.Сonstants
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

class SettingsDataStore(private val settings: Settings) {
    fun deleteRootDirectory() {
        settings.putString(Сonstants.ROOT_DIRECTORY, "")
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun saveRootDirectoryBookmark(bytes: ByteArray) {
        settings.putString(Сonstants.ROOT_DIRECTORY, Base64.encode(bytes))
    }

    @OptIn(ExperimentalEncodingApi::class)
    fun getRootDirectoryBookmark(): ByteArray? {
        val b64 = settings.getString(Сonstants.ROOT_DIRECTORY, "")
        if (b64.isBlank()) return null
        return runCatching { Base64.decode(b64) }.getOrNull()
    }

    fun saveEntryOnBoarding() {
        settings.putBoolean(Сonstants.ONBOARDING_ENTRY, true)
    }

    fun getEntryOnBoarding(): Boolean {
        return settings.getBoolean(Сonstants.ONBOARDING_ENTRY, false)
    }

    fun clearEntryOnBoarding() {
        settings.putBoolean(Сonstants.ONBOARDING_ENTRY, false)
    }
}