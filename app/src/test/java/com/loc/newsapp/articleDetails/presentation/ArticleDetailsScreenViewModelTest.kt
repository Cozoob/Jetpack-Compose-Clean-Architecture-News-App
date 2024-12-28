package com.loc.newsapp.articleDetails.presentation

import com.loc.newsapp.MainDispatcherRule
import com.loc.newsapp.bookmark.domain.ArticlesUseCases
import com.loc.newsapp.sharedtest.core.domain.ArticleTestFactory
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ArticleDetailsScreenViewModelTest {
  @get:Rule val rule = MainDispatcherRule()

  private lateinit var viewModel: ArticleDetailsScreenViewModel
  private val articlesUseCases: ArticlesUseCases = mockk(relaxed = true)
  private val articleDetailsScreenIntent: ArticleDetailsScreenIntent = mockk(relaxed = true)
  private val article = ArticleTestFactory.createArticle()

  @Before
  fun setup() {
    mockArticleDetailsScreenLogger()
  }

  @Test
  fun `Init view model WHEN article is bookmarked`() = runTest {
    givenArticleInDatabase()
    whenViewModelInit()
    thenArticleIsBookmarked()
  }

  @Test
  fun `Init view model WHEN article is not bookmarked`() = runTest {
    givenArticleNotInDatabase()
    whenViewModelInit()
    thenArticleIsNotBookmarked()
  }

  @Test
  fun `Save article in database WHEN article not found in local database`() = runTest {
    givenArticleNotInDatabaseAndInitViewModel()
    whenActionSaveArticle()
    thenArticleIsBookmarked()
  }

  @Test
  fun `Delete article in database WHEN article found in local database`() = runTest {
    givenArticleInDatabaseAndInitViewModel()
    whenActionSaveArticle()
    thenArticleIsNotBookmarked()
  }

  @Test
  fun `Share article`() = runTest {
    givenArticleInDatabaseAndInitViewModel()
    assertDoesNotThrow { viewModel.onAction(action = ArticleDetailsScreenAction.ShareArticle) }
  }

  @Test
  fun `Browse article`() = runTest {
    givenArticleInDatabaseAndInitViewModel()
    assertDoesNotThrow { viewModel.onAction(action = ArticleDetailsScreenAction.BrowseArticle) }
  }

  private fun givenArticleInDatabase() {
    coEvery { articlesUseCases.findByUrlArticle(url = any()) } returns article
  }

  private fun givenArticleNotInDatabase() {
    coEvery { articlesUseCases.findByUrlArticle(url = any()) } returns null
  }

  private fun givenArticleNotInDatabaseAndInitViewModel() {
    coEvery { articlesUseCases.findByUrlArticle(url = any()) } returns null
    viewModel =
        ArticleDetailsScreenViewModel(
            article = article,
            articlesUseCases = articlesUseCases,
            articleDetailsScreenIntent = articleDetailsScreenIntent)
  }

  private fun givenArticleInDatabaseAndInitViewModel() {
    coEvery { articlesUseCases.findByUrlArticle(url = any()) } returns article
    viewModel =
        ArticleDetailsScreenViewModel(
            article = article,
            articlesUseCases = articlesUseCases,
            articleDetailsScreenIntent = articleDetailsScreenIntent)
  }

  private fun whenViewModelInit() {
    viewModel =
        ArticleDetailsScreenViewModel(
            article = article,
            articlesUseCases = articlesUseCases,
            articleDetailsScreenIntent = articleDetailsScreenIntent)
  }

  private fun whenActionSaveArticle() {
    viewModel.onAction(action = ArticleDetailsScreenAction.SaveArticle)
  }

  private fun thenArticleIsBookmarked() {
    assertTrue(viewModel.state.isArticleBookmarked)
  }

  private fun thenArticleIsNotBookmarked() {
    assertFalse(viewModel.state.isArticleBookmarked)
  }

  private fun mockArticleDetailsScreenLogger() {
    mockkObject(ArticleDetailsScreenLogger)
    every { ArticleDetailsScreenLogger.logScreenView() } just Runs
    every { ArticleDetailsScreenLogger.logBookmark(any()) } just Runs
    every { ArticleDetailsScreenLogger.logShare() } just Runs
    every { ArticleDetailsScreenLogger.logOpenArticleInBrowser() } just Runs
  }
}
