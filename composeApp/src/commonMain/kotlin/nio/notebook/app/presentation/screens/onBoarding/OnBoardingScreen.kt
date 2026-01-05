package nio.notebook.app.presentation.screens.onBoarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import io.github.aakira.napier.Napier
import nio.notebook.app.presentation.navigation.AppRouter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnBoardingScreen(
    onBoardingViewModel: OnBoardingViewModel = koinViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier,
) {

    val currentPage by onBoardingViewModel.currentPage.collectAsState()
    val pagerState = rememberPagerState(initialPage = currentPage) { 3 }

    LaunchedEffect(currentPage) {
        pagerState.animateScrollToPage(currentPage)
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> OnBoardingElement(
                progress = page,
                progressCount = 3,
                title = "Welcome to NIO",
                content = "Discover amazing features that will help you in your daily tasks.",
                onNextClicked = { onBoardingViewModel.onNextPage(currentPage + 1) },
            )

            1 -> OnBoardingElement(
                progress = page,
                progressCount = 3,
                title = "Easy to Use",
                content = "Our intuitive interface makes it simple to get started.",
                onNextClicked = { onBoardingViewModel.onNextPage(currentPage + 1) },
            )

            2 -> OnBoardingElement(
                progress = page,
                progressCount = 3,
                title = "Get Started",
                content = "Join thousands of satisfied users today.",
                onNextClicked = {
                    onBoardingViewModel.saveEntry()
                    navController.navigate(AppRouter.DirectorySelector.route) {
                        popUpTo(AppRouter.DirectorySelector.route) { inclusive = true }
                    }
                },
            )
        }
    }
}