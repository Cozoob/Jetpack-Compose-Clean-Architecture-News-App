package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.TestTag
import com.loc.newsapp.core.domain.annotation.DayNightPreviews

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
  Column(
      modifier = modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center) {
        Icon(
            painter = painterResource(id = R.drawable.icon_logo),
            contentDescription = stringResource(id = R.string.common_icon_logo_contentDescription),
            tint = if (isSystemInDarkTheme()) LightGray else DarkGray,
            modifier =
                Modifier.size(120.dp).alpha(alpha = 0.3f).testTag(tag = TestTag.EMPTY_CONTENT_LOGO))
        Text(
            modifier = Modifier.padding(10.dp),
            text = stringResource(id = R.string.core_text_noContent_text),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSystemInDarkTheme()) LightGray else DarkGray,
        )
      }
}

@DayNightPreviews
@Composable
private fun EmptyContent_Default_Preview() {
  NewsAppPreviewSurface(content = { EmptyContent() })
}
