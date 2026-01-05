package nio.notebook.app.di

import org.koin.dsl.module
import com.russhwolf.settings.Settings
import nio.notebook.app.data.storage.SettingsDataStore
import nio.notebook.app.domain.model.createSettings

val settingsModule = module {
    single<Settings> { createSettings() }
    single<SettingsDataStore> { SettingsDataStore(get()) }
}