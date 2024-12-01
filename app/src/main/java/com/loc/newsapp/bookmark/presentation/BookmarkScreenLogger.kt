package com.loc.newsapp.bookmark.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

object BookmarkScreenLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "BookmarkScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "BookmarkScreenViewModel")
    }
  }
}
