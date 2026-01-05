package nio.notebook.app.di

import nio.notebook.app.presentation.screens.directorySelector.DirectorySelectorViewModel
import nio.notebook.app.presentation.screens.onBoarding.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule  = module {
    viewModel { OnBoardingViewModel(get(), get()) }
    viewModel { DirectorySelectorViewModel(get(), get()) }
}