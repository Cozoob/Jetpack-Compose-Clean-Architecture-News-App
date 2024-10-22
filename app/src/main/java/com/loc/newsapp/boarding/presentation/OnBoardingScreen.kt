package com.loc.newsapp.boarding.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.boarding.presentation.components.OnBoardingPage
import com.loc.newsapp.boarding.presentation.components.PageIndicator
import com.loc.newsapp.core.presentation.Dimensions.MediumPadding2
import com.loc.newsapp.core.presentation.Dimensions.PageIndicatorWidth
import com.loc.newsapp.core.presentation.NewsButton
import com.loc.newsapp.core.presentation.NewsTextButton
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreenRoot(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    Surface {
        OnBoardingScreen(
            state = viewModel.state,
            onAction = viewModel::onAction
        )
    }
}

@Composable
fun OnBoardingScreen(
    state: OnBoardingState,
    onAction: (OnBoardingAction) -> Unit
) {
    val pagerState = rememberPagerState(initialPage = state.pageIndex) {
        state.pages.size
    }
    val buttonState = remember {
        derivedStateOf {
            when(pagerState.currentPage) {
                0 -> listOf("", "Next")
                1 -> listOf("Back", "Next")
                2 -> listOf("Back", "Get Started")
                else -> listOf("", "")
            }
        }
    }
    val coroutineScope = rememberCoroutineScope()

    if(state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(state = pagerState) { index ->
                OnBoardingPage(page = state.pages[index])
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MediumPadding2)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PageIndicator(
                    modifier = Modifier.width(PageIndicatorWidth),
                    numberOfPages = state.pages.size,
                    selectedPageIndex = pagerState.currentPage
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val backButtonMessage = buttonState.value[0]
                    val nextButtonMessage = buttonState.value[1]
                    val isBackButtonShown = backButtonMessage.isNotEmpty()

                    if(isBackButtonShown) {
                        NewsTextButton(
                            text = backButtonMessage,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage - 1
                                    )
                                }
                            }
                        )
                    }

                    NewsButton(
                        text = nextButtonMessage,
                        onClick = {
                            coroutineScope.launch {
                                if (pagerState.currentPage == 3) {
                                    // TODO: Navigate to Home Screen
                                } else {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }
}