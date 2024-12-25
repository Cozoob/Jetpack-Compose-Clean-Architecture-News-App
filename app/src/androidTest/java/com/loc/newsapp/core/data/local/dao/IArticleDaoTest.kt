package com.loc.newsapp.core.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import com.loc.newsapp.core.data.local.LocalDatabase
import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.sharedtest.core.domain.ArticleTestFactory
import com.loc.newsapp.util.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class IArticleDaoTest {
  @get:Rule var hiltRule = HiltAndroidRule(this)

  @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Inject @Named("TestDatabase") lateinit var database: LocalDatabase
  private lateinit var dao: IArticleDao
  private val articles = mutableListOf<Article>()

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
  fun delete_1_Article() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 1)
    whenDeleteArticles(articles = articles)
    thenDatabaseShouldNotHaveArticles(articles = articles)
  }

  @Test
  fun get_3_Articles() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 3)
    whenDeleteArticles(articles = articles)
    thenDatabaseShouldNotHaveArticles(articles = articles)
  }

  @Test
  fun find_1_Article_By_Url() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 1)
    val foundArticle = whenFindArticleByUrl(url = articles[0].url)
    thenArticleIsNotNull(article = foundArticle)
  }

  @Test
  fun not_Found_Article_By_Url_WHEN_Empty_Database() = runTest {
    val notSavedArticle = givenArticleNotSavedInDatabase()
    val foundArticle = whenFindArticleByUrl(url = notSavedArticle.url)
    thenArticleIsNull(article = foundArticle)
  }

  @Test
  fun found_1_Article_By_Urls() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 1)
    val foundArticles = whenFindArticlesByUrls(articles = articles)
    thenArticlesHasSizeN(articles = foundArticles, size = 1)
  }

  @Test
  fun not_Found_Article_By_Urls_WHEN_Empty_Database() = runTest {
    givenNArticlesNotInDatabase(numberOfArticles = 1)
    val foundArticles = whenFindArticlesByUrls(articles = articles)
    thenArticlesHasSizeN(articles = foundArticles, size = 0)
  }

  @Test
  fun found_1_Article_By_Titles() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 1)
    val foundArticles = whenFindArticlesByTitles(articles = articles)
    thenArticlesHasSizeN(articles = foundArticles, size = 1)
  }

  @Test
  fun not_Found_Article_By_Titles_WHEN_Empty_Database() = runTest {
    givenNArticlesNotInDatabase(numberOfArticles = 1)
    val foundArticles = whenFindArticlesByTitles(articles = articles)
    thenArticlesHasSizeN(articles = foundArticles, size = 0)
  }

  @Test
  fun upsert_1_Article() = runTest {
    givenNArticlesInDatabase(numberOfArticles = 1)
    val allArticles = whenObserveAllArticles()
    thenArticlesContainsOtherArticles(articles = allArticles, otherArticles = articles)
  }

  private suspend fun givenNArticlesInDatabase(numberOfArticles: Int) {
    articles.clear()

    repeat(numberOfArticles) {
      val article = ArticleTestFactory.createArticle()
      articles.add(article)
      dao.upsert(article)
    }
  }

  private fun givenArticleNotSavedInDatabase(): Article {
    return ArticleTestFactory.createArticle()
  }

  private fun givenNArticlesNotInDatabase(numberOfArticles: Int) {
    articles.clear()

    repeat(numberOfArticles) {
      val article = ArticleTestFactory.createArticle()
      articles.add(article)
    }
  }

  private suspend fun whenDeleteArticles(articles: MutableList<Article>) {
    repeat(articles.size) {
      val article = articles[it]
      dao.delete(article)
    }
  }

  private suspend fun whenFindArticleByUrl(url: String): Article? {
    return dao.findByUrl(url)
  }

  private fun whenObserveAllArticles(): List<Article> {
    return dao.observeAllArticles().getOrAwaitValue()
  }

  private fun whenFindArticlesByUrls(articles: List<Article>): List<Article> {
    val urls: MutableList<String> = mutableListOf()
    repeat(articles.size) { urls.add(articles[it].url) }

    return dao.findByUrls(urls = urls).asLiveData().getOrAwaitValue()
  }

  private fun whenFindArticlesByTitles(articles: List<Article>): List<Article> {
    val titles: MutableList<String> = mutableListOf()
    repeat(articles.size) { titles.add(articles[it].title) }

    return dao.findByTitles(titles = titles).asLiveData().getOrAwaitValue()
  }

  private fun thenDatabaseShouldNotHaveArticles(articles: MutableList<Article>) {
    val allArticles = dao.observeAllArticles().getOrAwaitValue()
    repeat(articles.size) { assertThat(allArticles).doesNotContain(articles[it]) }
  }

  private fun thenArticleIsNotNull(article: Article?) {
    assertThat(article).isNotNull()
  }

  private fun thenArticlesContainsOtherArticles(
      articles: List<Article>,
      otherArticles: List<Article>
  ) {
    assertThat(articles).containsExactlyElementsIn(otherArticles)
  }

  private fun thenArticleIsNull(article: Article?) {
    assertThat(article).isNull()
  }

  private fun thenArticlesHasSizeN(articles: List<Article>, size: Int) {
    assertThat(articles).hasSize(size)
  }
}
