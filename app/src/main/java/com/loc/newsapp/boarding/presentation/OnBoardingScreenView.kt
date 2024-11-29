package com.loc.newsapp.boarding.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.R
import com.loc.newsapp.boarding.domain.model.Page
import com.loc.newsapp.boarding.presentation.component.OnBoardingPage
import com.loc.newsapp.boarding.presentation.component.PageIndicator
import com.loc.newsapp.core.presentation.component.NewsButton
import com.loc.newsapp.core.presentation.component.NewsCircularProgressIndicator
import com.loc.newsapp.core.presentation.component.NewsTextButton
import com.loc.newsapp.core.presentation.constant.Dimensions.MediumPadding2
import com.loc.newsapp.core.presentation.constant.Dimensions.PageIndicatorWidth
import com.loc.newsapp.ui.theme.NewsAppTheme
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
              Modifier.fillMaxWidth().padding(horizontal = MediumPadding2).navigationBarsPadding(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
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

@Preview(name = "On Boarding Screen, 3 pages, light mode", group = "Pages", showBackground = true)
@Preview(
    name = "On Boarding Screen, 3 pages, dark mode",
    group = "Pages",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@PreviewScreenSizes
@Composable
private fun OnBoardingScreenPreviewWithPages() {
  NewsAppTheme {
    Surface {
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
    }
  }
}

@Preview(
    name = "On Boarding Screen, loading data, light mode", group = "Loading", showBackground = true)
@Preview(
    name = "On Boarding Screen, loading data, dark mode",
    group = "Loading",
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true)
@PreviewScreenSizes
@Composable
private fun OnBoardingScreenPreviewWithLoading() {
  NewsAppTheme {
    Surface {
      OnBoardingScreenViewContent(
          state =
              OnBoardingScreenState(
                  pages = emptyList(),
                  pageIndex = 0,
                  isLoading = true,
              ),
          onAction = {})
    }
  }
}
