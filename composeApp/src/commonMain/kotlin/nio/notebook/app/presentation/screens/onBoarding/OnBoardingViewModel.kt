package nio.notebook.app.presentation.screens.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nio.notebook.app.core.errorHandler.handler.ErrorHandler
import nio.notebook.app.core.errorHandler.model.ErrorEntity
import nio.notebook.app.core.errorHandler.model.ErrorPriority
import nio.notebook.app.core.errorHandler.model.ErrorType
import nio.notebook.app.data.storage.SettingsDataStore

class OnBoardingViewModel(private val errorHandler: ErrorHandler, private val settingsDataStore: SettingsDataStore): ViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage

    val totalPages = 3

    fun onNextPage(page: Int) {
        if (page < totalPages) {
            _currentPage.value = page
        }
    }

    fun saveEntry(){
        viewModelScope.launch {
            settingsDataStore.saveEntryOnBoarding()
        }
    }

    fun onPreviousPage(page: Int) {
        if (page > 0) {
            _currentPage.value = page
        }
    }
}