package com.loc.newsapp.bookmark.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.loc.newsapp.R
import com.loc.newsapp.core.domain.routes.ArticleDetailsRoute
import com.loc.newsapp.core.presentation.components.ArticlesList
import com.loc.newsapp.core.presentation.constants.Dimensions.MediumPadding1

@Composable
fun BookmarkScreenRoot(
    navController: NavController,
    viewModel: BookmarkScreenViewModel = hiltViewModel()
) {
  BookmarkScreen(
      state = viewModel.state,
      onAction = { action ->
        when (action) {
          is BookmarkScreenAction.NavigateToArticleDetails ->
              navController.navigate(ArticleDetailsRoute(article = action.article))

          else -> Unit
        }
      })
}

@Composable
private fun BookmarkScreen(state: BookmarkScreenState, onAction: (BookmarkScreenAction) -> Unit) {
  Column(
      modifier =
          Modifier.fillMaxWidth()
              .statusBarsPadding()
              .padding(top = MediumPadding1, start = MediumPadding1, end = MediumPadding1)) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.text_title))
        Spacer(modifier = Modifier.height(MediumPadding1))
        ArticlesList(
            articles = state.articles,
            onClick = { onAction.invoke(BookmarkScreenAction.NavigateToArticleDetails(it)) })
      }
}
