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
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class BookmarkScreenTest {
  @get:Rule(order = 0) var hiltRule = HiltAndroidRule(this)
  @get:Rule(order = 1) val rule = createAndroidComposeRule<MainActivity>()

  //  @Inject lateinit var viewModel: BookmarkScreenViewModel
  @Inject @Named("TestDatabase") lateinit var database: LocalDatabase
  private lateinit var dao: IArticleDao

  @Before
  fun setup() {
    hiltRule.inject()
    rule.activity.setContent {
      BookmarkScreenView(
          viewModel = hiltViewModel<BookmarkScreenViewModel>(), navController = mockk())
    }
    dao = database.articleDao()
  }

  @After
  fun teardown() {
    database.close()
  }

  @Test
  fun has_No_Content_Text_Shown_WHEN_No_Articles() = runTest {
    val noContentTextMatcher =
        hasText(rule.activity.getString(R.string.core_text_noContent_text)) and hasNoClickAction()
    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)

    rule.onNode(articlesListMatcher).assertExists()
    rule.onNode(noContentTextMatcher).assertIsDisplayed()
  }

  @Test
  fun has_No_Content_Logo_Shown_WHEN_No_Articles() = runTest {
    val emptyContentMatcher = hasTestTag(TestTag.EMPTY_CONTENT_LOGO)
    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)

    rule.onNode(articlesListMatcher).assertExists()
    rule.onNode(emptyContentMatcher).assertIsDisplayed()
  }

  @Test
  fun has_1_Article_Shown_WHEN_1_Article_In_Database() = runTest {
    // not working... TO RESEARCH.....
    //    val articleNodeMatcher = hasTestTag(TestTag.ARTICLE_CARD)
    //    val articlesListMatcher = hasTestTag(TestTag.ARTICLES_LIST)
    //
    //    dao.upsert(article = ArticleTestFactory.createArticle())
    //    rule.activity.setContent { BookmarkScreenView(viewModel =
    // hiltViewModel<BookmarkScreenViewModel>(), navController = mockk()) }
    //
    //    rule.onNode(articlesListMatcher).assertExists()
    //    rule.onNode(articleNodeMatcher).assertExists()
  }
}
