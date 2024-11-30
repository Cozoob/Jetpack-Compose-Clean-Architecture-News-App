package com.loc.newsapp.boarding.data.dataSource

import com.loc.newsapp.R
import com.loc.newsapp.boarding.domain.model.Page

class PageStaticService : IPageDAO {
  // I do not translate it since it should be done by this "external" service.
  // I can pass in the future the language parameter to return proper strings.
  override fun getPages(): List<Page> {
    return listOf(
        Page(
            title = "Stay Informed, Anytime, Anywhere",
            description =
                "Get the latest news from trusted sources worldwide, delivered straight to your " +
                    "device. Stay updated with breaking stories, local updates, and global events, all in one place.",
            image = R.drawable.image_onboarding_bitcoin),
        Page(
            title = "Personalized News, Just for You",
            description =
                "Customize your news feed by selecting topics and regions that matter most to you." +
                    " Receive tailored content, from sports to politics, based on your preferences.",
            image = R.drawable.image_onboarding_japan),
        Page(
            title = "Real-Time Alerts & Updates",
            description =
                "Never miss an important update! Enable notifications to get real-time alerts on" +
                    " major news, live events, and stories that impact you directly.",
            image = R.drawable.image_onboarding_jar))
  }
}
