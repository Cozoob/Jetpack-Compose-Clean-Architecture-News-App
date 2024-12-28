package com.loc.newsapp.bookmark.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasNoClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import com.loc.newsapp.R
import com.loc.newsapp.core.constant.TestTag
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.data.local.dao.IArticleDao
import com.loc.newsapp.core.presentation.MainActivity
import com.loc.newsapp.sharedtest.core.domain.ArticleTestFactory
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BookmarkScreenTest {
  @get:Rule(order = 0) var hiltRule = HiltAndroidRule(this)
  @get:Rule(order = 1) val rule = createAndroidComposeRule<MainActivity>()

  @Inject @Named("TestDatabase") lateinit var database: LocalDatabase
  private lateinit var dao: IArticleDao

  @Before
  fun setup() {
    hiltRule.inject()
    dao = database.articleDao()
  }

  @After
  fun teardown() {
    database.close()
  }

  @Test
  fun test_GIVEN_No_Articles_WHEN_Init_View_THEN_No_Content_Text_Displayed() = runTest {
    val noContentTextMatcher =
        hasText(rule.activity.getString(R.string.core_text_noContent_text)) and hasNoClickAction()
    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)

    rule.activity.setContent {
      BookmarkScreenView(
          viewModel = hiltViewModel<BookmarkScreenViewModel>(), navController = mockk())
    }

    with(rule) {
      onNode(articlesListMatcher).assertExists("Articles list does not exist!")
      onNode(noContentTextMatcher).assertIsDisplayed()
    }
  }

  @Test
  fun test_GIVEN_No_Articles_WHEN_Init_View_THEN_No_Content_Logo_Displayed() = runTest {
    val emptyContentMatcher = hasTestTag(TestTag.EMPTY_CONTENT_LOGO)
    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)

    rule.activity.setContent {
      BookmarkScreenView(
          viewModel = hiltViewModel<BookmarkScreenViewModel>(), navController = mockk())
    }

    with(rule) {
      onNode(articlesListMatcher).assertExists("Articles list does not exist!")
      onNode(emptyContentMatcher).assertIsDisplayed()
    }
  }

  @Test
  fun test_GIVEN_3_Articles_WHEN_Init_View_THEN_3_Article_Cards_Displayed() = runTest {
    val articleNodeMatcher = hasTestTag(TestTag.ARTICLE_CARD)
    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)
    val articles =
        listOf(
            ArticleTestFactory.createArticle(),
            ArticleTestFactory.createArticle(),
            ArticleTestFactory.createArticle())
    repeat(articles.size) { idx -> dao.upsert(article = articles[idx]) }

    rule.activity.setContent {
      BookmarkScreenViewContent(state = BookmarkScreenState(articles = articles), onAction = {})
    }

    rule.onNode(articlesListMatcher).assertExists("Articles list does not exist!")
    Assert.assertEquals(
        articles.size, rule.onAllNodes(articleNodeMatcher).fetchSemanticsNodes().size)
  }
}
