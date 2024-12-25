package com.loc.newsapp.core.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.DimensionConstant.ARTICLE_CARD_SIZE
import com.loc.newsapp.core.constant.DimensionConstant.EXTRA_SMALL_PADDING
import com.loc.newsapp.core.constant.DimensionConstant.SMALL_PADDING
import com.loc.newsapp.core.domain.annotation.DayNightPreviews
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source
import com.loc.newsapp.core.presentation.util.debugPlaceholder

@Composable
fun ArticleCard(modifier: Modifier = Modifier, article: Article, onClick: () -> Unit) {
  val context = LocalContext.current

  Row(modifier = modifier.clickable { onClick() }) {
    AsyncImage(
        modifier = Modifier.size(ARTICLE_CARD_SIZE).clip(MaterialTheme.shapes.medium),
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context).data(article.urlToImage).build(),
        contentDescription =
            stringResource(
                id = R.string.core_asyncImage_articleImage_contentDescription,
                article.title,
                article.description),
        placeholder = debugPlaceholder(R.drawable.image_placeholder_horizontal),
        error = painterResource(id = R.drawable.image_no_image_available))
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(horizontal = SMALL_PADDING).height(ARTICLE_CARD_SIZE)) {
          Text(
              text = article.title,
              style = MaterialTheme.typography.bodyMedium,
              color = colorResource(id = R.color.text_title),
              maxLines = 2,
              overflow = TextOverflow.Ellipsis)
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = article.source.name,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.body))
            Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
            Icon(
                painter = painterResource(id = R.drawable.icon_time),
                contentDescription =
                    stringResource(id = R.string.common_icon_time_contentDescription),
                modifier = Modifier.size(11.dp))
            Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
            Text(
                text = article.publishedAt,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = colorResource(id = R.color.body))
          }
        }
  }
}

@DayNightPreviews
@Composable
private fun ArticleCard_SampleArticle_Preview() {
  NewsAppPreviewSurface(
      content = {
        ArticleCard(
            article =
                Article(
                    author = "John Smith",
                    content =
                        "Recent studies reveal that advancements in AI technology are transforming " +
                            "industries at an unprecedented rate.",
                    description =
                        "An in-depth look at how AI is reshaping the future of work and daily life.",
                    publishedAt = "2024.11.20",
                    source = Source(id = "techcrunch", name = "TechCrunch"),
                    title = "The Rise of AI: Opportunities and Challenges",
                    url = "https://www.techcrunch.com/articles/rise-of-ai",
                    urlToImage = "https://www.techcrunch.com/images/ai-article.jpg"),
            onClick = {})
      })
}
