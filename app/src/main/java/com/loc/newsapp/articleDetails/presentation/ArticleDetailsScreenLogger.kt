package com.loc.newsapp.articleDetails.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

object ArticleDetailsScreenLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "ArticleDetailsScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "ArticleDetailsScreenViewModel")
    }
  }

  fun logBookmark(isBookmarked: Boolean) {
    Firebase.analytics.logEvent("bookmark") {
      param("action", if (isBookmarked) "remove" else "add")
    }
  }

  fun logShare() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SHARE) { param("is_shared", 1) }
  }

  fun logOpenArticleInBrowser() {
    Firebase.analytics.logEvent("open_article_in_browser") { param("is_opened", 1) }
  }
}
