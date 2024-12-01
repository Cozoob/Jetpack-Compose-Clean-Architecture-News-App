package com.loc.newsapp.home.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

object HomeScreenLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "HomeScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "HomeScreenViewModel")
    }
  }

  fun logLoadNewsStart(newsSources: String) {
    Firebase.analytics.logEvent("load_news_start") {
      param("news_sources", newsSources)
      param("is_loading", 1)
    }
  }

  fun logLoadNewsComplete(newsSources: String, newsCount: Long) {
    Firebase.analytics.logEvent("load_news_complete") {
      param("news_sources", newsSources)
      param("news_count", newsCount)
      param("is_loading", 0)
    }
  }
}
