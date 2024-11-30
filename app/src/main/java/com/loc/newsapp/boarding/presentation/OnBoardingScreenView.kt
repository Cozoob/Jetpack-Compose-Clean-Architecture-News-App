package com.loc.newsapp.boarding.presentation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.R
import com.loc.newsapp.boarding.domain.model.Page
import com.loc.newsapp.boarding.presentation.component.OnBoardingPage
import com.loc.newsapp.boarding.presentation.component.PageIndicator
import com.loc.newsapp.core.constant.DimensionConstants.EXTRA_BIG_PADDING
import com.loc.newsapp.core.constant.DimensionConstants.PAGE_INDICATOR_WIDTH
import com.loc.newsapp.core.domain.annotation.DayNightInSystemUiPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.component.NewsButton
import com.loc.newsapp.core.presentation.component.NewsCircularProgressIndicator
import com.loc.newsapp.core.presentation.component.NewsTextButton
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreenView(viewModel: OnBoardingScreenViewModel = hiltViewModel()) {
  OnBoardingScreenViewContent(state = viewModel.state, onAction = viewModel::onAction)
}

@Composable
private fun OnBoardingScreenViewContent(
    state: OnBoardingScreenState,
    onAction: (OnBoardingScreenAction) -> Unit
) {
  val pagerState = rememberPagerState(initialPage = state.pageIndex) { state.pages.size }
  val buttonState = remember {
    derivedStateOf {
      when (pagerState.currentPage) {
        0 -> listOf("", "Next")
        1 -> listOf("Back", "Next")
        2 -> listOf("Back", "Get Started")
        else -> listOf("", "")
      }
    }
  }
  val coroutineScope = rememberCoroutineScope()

  if (state.isLoading) {
    NewsCircularProgressIndicator()
  } else {
    Column(modifier = Modifier.fillMaxSize()) {
      HorizontalPager(state = pagerState) { index -> OnBoardingPage(page = state.pages[index]) }
      Spacer(modifier = Modifier.weight(1f))
      Row(
          modifier =
              Modifier.fillMaxWidth()
                  .padding(horizontal = EXTRA_BIG_PADDING)
                  .navigationBarsPadding(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically) {
            PageIndicator(
                modifier = Modifier.width(PAGE_INDICATOR_WIDTH),
                numberOfPages = state.pages.size,
                selectedPageIndex = pagerState.currentPage)
            Row(verticalAlignment = Alignment.CenterVertically) {
              val backButtonMessage = buttonState.value[0]
              val nextButtonMessage = buttonState.value[1]
              val isBackButtonShown = backButtonMessage.isNotEmpty()

              if (isBackButtonShown) {
                NewsTextButton(
                    text = backButtonMessage,
                    onClick = {
                      coroutineScope.launch {
                        pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                      }
                    })
              }

              NewsButton(
                  text = nextButtonMessage,
                  onClick = {
                    coroutineScope.launch {
                      val isLastPage = pagerState.currentPage == state.pages.size - 1

                      if (isLastPage) {
                        onAction.invoke(OnBoardingScreenAction.LogFirstAppEntry)
                      } else {
                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                      }
                    }
                  })
            }
          }
      Spacer(modifier = Modifier.weight(weight = 0.5f))
    }
  }
}

@DayNightInSystemUiPreviews
@PreviewScreenSizes
@Composable
private fun OnBoardingScreenView_ThreePages_Preview() {
  NewsAppPreviewSurface(
      content = {
        OnBoardingScreenViewContent(
            state =
                OnBoardingScreenState(
                    pages =
                        listOf(
                            Page(
                                title = "Stay Informed, Anytime, Anywhere",
                                description =
                                    "Get the latest news from trusted sources worldwide, delivered straight " +
                                        "to your device. Stay updated with breaking stories, " +
                                        "local updates, and global events, all in one place.",
                                image = R.drawable.onboarding1),
                            Page(
                                title = "Stay Informed, Anytime, Anywhere",
                                description =
                                    "Get the latest news from trusted sources worldwide, delivered " +
                                        "straight to your device. Stay updated with breaking stories," +
                                        " local updates, and global events, all in one place.",
                                image = R.drawable.onboarding1),
                            Page(
                                title = "Stay Informed, Anytime, Anywhere",
                                description =
                                    "Get the latest news from trusted sources worldwide, delivered straight " +
                                        "to your device. Stay updated with breaking stories, local updates," +
                                        " and global events, all in one place.",
                                image = R.drawable.onboarding1)),
                    pageIndex = 0,
                    isLoading = false,
                ),
            onAction = {})
      })
}

@DayNightInSystemUiPreviews
@PreviewScreenSizes
@Composable
private fun OnBoardingScreen_Loading_Preview() {
  NewsAppPreviewSurface(
      content = {
        OnBoardingScreenViewContent(
            state =
                OnBoardingScreenState(
                    pages = emptyList(),
                    pageIndex = 0,
                    isLoading = true,
                ),
            onAction = {})
      })
}