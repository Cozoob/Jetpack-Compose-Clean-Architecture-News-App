package com.loc.newsapp.boarding.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.loc.newsapp.R
import com.loc.newsapp.boarding.domain.model.Page
import com.loc.newsapp.core.domain.model.DayNightPreviews
import com.loc.newsapp.core.presentation.component.NewsAppPreviewSurface
import com.loc.newsapp.core.presentation.constant.Dimensions.MediumPadding1
import com.loc.newsapp.core.presentation.constant.Dimensions.MediumPadding2

@Composable
fun OnBoardingPage(modifier: Modifier = Modifier, page: Page) {
  Column(modifier = modifier) {
    Image(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.6f),
        painter = painterResource(id = page.image),
        contentDescription = null,
        contentScale = ContentScale.Crop)
    Spacer(modifier = Modifier.height(MediumPadding1))
    Text(
        text = page.title,
        modifier = Modifier.padding(horizontal = MediumPadding2),
        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
        color = colorResource(id = R.color.display_small))
    Text(
        text = page.description,
        modifier = Modifier.padding(horizontal = MediumPadding2),
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.text_medium))
  }
}

@DayNightPreviews
@Composable
private fun OnBoardingPage_SamplePage_Preview() {
  NewsAppPreviewSurface(
      content = {
        OnBoardingPage(
            page =
                Page(
                    title = "Stay Informed, Anytime",
                    description =
                        "Access breaking news and top stories from around the globe," +
                            " right at your fingertips. Our app delivers real-time updates" +
                            " from trusted sources, ensuring you’re always in the know. " +
                            "Whether it's world news, local stories, or trending topics," +
                            " we've got you covered 24/7.",
                    image = R.drawable.onboarding1))
      })
}
