package com.loc.newsapp.search.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

object SearchScreenLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "SearchScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "SearchScreenViewModel")
    }
  }

  fun logSearch(searchValue: String) {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SEARCH) {
      param(FirebaseAnalytics.Param.VALUE, searchValue)
    }
  }
}
