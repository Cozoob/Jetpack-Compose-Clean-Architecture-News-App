package com.loc.newsapp.core.domain.factory

import com.loc.newsapp.core.domain.model.Article
import com.loc.newsapp.core.domain.model.Source

object ArticleTestFactory {
  fun createArticle(): Article {
    return buildArticle()
  }

  fun createArticleWithTitle(title: String): Article {
    return buildArticle(title = title)
  }

  private fun buildArticle(
      author: String = "John Smith",
      content: String =
          "Recent studies reveal that advancements in AI technology are " +
              "transforming industries at an unprecedented rate.",
      description: String =
          "An in-depth look at how AI is reshaping the future of work and daily life.",
      publishedAt: String = "2024.11.20",
      source: Source = Source(id = "techcrunch", name = "TechCrunch"),
      title: String = "The Rise of AI: Opportunities and Challenges",
      url: String = "https://www.techcrunch.com/articles/rise-of-ai",
      urlToImage: String = "https://www.techcrunch.com/images/ai-article.jpg"
  ): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage)
  }
}
