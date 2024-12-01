package com.loc.newsapp.boarding.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.ktx.Firebase

object OnBoardingScreenLogger {
  fun logScreenView() {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
      param(FirebaseAnalytics.Param.SCREEN_NAME, "OnBoardingScreen")
      param(FirebaseAnalytics.Param.SCREEN_CLASS, "OnBoardingScreenViewModel")
    }
  }

  fun logFirstEntry() {
    Firebase.analytics.logEvent("first_entry") {
      param(FirebaseAnalytics.Param.VALUE, "is_first_entry")
    }
  }
}
